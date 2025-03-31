package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.ProjectApplicationDTO.CreateProjectApplicationDTO;
import com.connectbundle.connect.dto.ProjectApplicationDTO.ProjectApplicationDTO;
import com.connectbundle.connect.dto.ProjectsDTO.ProjectResponseDTO;
import com.connectbundle.connect.dto.ProjectsDTO.ProjectTeamMemberDTO;
import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.model.Project;
import com.connectbundle.connect.model.ProjectApplication;
import com.connectbundle.connect.model.ProjectTeamMember;
import com.connectbundle.connect.model.User.User;
import com.connectbundle.connect.model.enums.ProjectApplicationStatus;
import com.connectbundle.connect.model.enums.ProjectTeamRole;
import com.connectbundle.connect.repository.ProjectApplicationRepository;
import com.connectbundle.connect.repository.ProjectRepository;
import com.connectbundle.connect.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectApplicationService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectApplicationRepository projectApplicationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<BaseResponse<ProjectApplicationDTO>> applyToProject(CreateProjectApplicationDTO dto) {

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project", "ID", dto.getProjectId()));

        User applicant = userRepository.findByUsername(dto.getApplicantUserName())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username", dto.getApplicantUserName()));
        // Create the application with current date and status APPLIED
        ProjectApplication application = new ProjectApplication();
        application.setProject(project);
        application.setApplicant(applicant);
        application.setApplicationDate(LocalDate.now());
        application.setStatus(ProjectApplicationStatus.APPLIED);
        application.setMentorRequest(dto.isMentorRequest());
        // Save the application
        ProjectApplication savedApplication = projectApplicationRepository.save(application);
        // Map to response DTO
        ProjectApplicationDTO responseDto = modelMapper.map(savedApplication, ProjectApplicationDTO.class);
        return BaseResponse.success(responseDto, "Application submitted successfully", 1);
    }

    public ResponseEntity<BaseResponse<ProjectResponseDTO>> acceptApplication(Long applicationId) {
        // Fetch the application by its ID
        ProjectApplication application = projectApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("ProjectApplication", "ID", applicationId));
        // Ensure the application is still pending
        if (application.getStatus() != ProjectApplicationStatus.APPLIED) {
            return BaseResponse.error("Application is not in pending state", HttpStatus.BAD_REQUEST);
        }
        // Determine team role based on the mentor request flag
        ProjectTeamRole teamRole = application.isMentorRequest() ? ProjectTeamRole.MENTOR : ProjectTeamRole.TEAM_MEMBER;
        // Check if the applicant is already a member of the project
        boolean alreadyMember = application.getProject().getProjectTeamMembers()
                .stream()
                .anyMatch(member -> member.getUser().getId().equals(application.getApplicant().getId()));
        if (alreadyMember) {
            return BaseResponse.error("User is already a member of the project", HttpStatus.BAD_REQUEST);
        }
        // Create a new team member from the application and add to the project
        ProjectTeamMember teamMember = new ProjectTeamMember();
        teamMember.setProject(application.getProject());
        teamMember.setUser(application.getApplicant());
        teamMember.setRole(teamRole);
        application.getProject().getProjectTeamMembers().add(teamMember);
        // Update application status to APPROVED
        application.setStatus(ProjectApplicationStatus.ACCEPTED);
        // Save the updated project and application
        Project updatedProject = projectRepository.save(application.getProject());
        projectApplicationRepository.save(application);
        // Map updated project to response DTO and manually map the team members list
        ProjectResponseDTO projectResponseDTO = modelMapper.map(updatedProject, ProjectResponseDTO.class);
        List<ProjectTeamMemberDTO> teamMembers = updatedProject.getProjectTeamMembers()
                .stream()
                .map(ptm -> modelMapper.map(ptm, ProjectTeamMemberDTO.class))
                .collect(Collectors.toList());
        projectResponseDTO.setProjectTeamMembers(teamMembers);
        return BaseResponse.success(projectResponseDTO, "Application accepted and member added", 1);
    }
}
