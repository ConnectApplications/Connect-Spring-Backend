package com.connectbundle.connect.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.ProjectsDTO.ProjectResponseDTO;
import com.connectbundle.connect.dto.ProjectsDTO.UpdateProjectDTO;

import jakarta.transaction.Transactional;

import org.apache.catalina.connector.Response;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.connectbundle.connect.dto.ProjectsDTO.AddUserToApplicationDTO;
import com.connectbundle.connect.dto.ProjectsDTO.CreateProjectDTO;
import com.connectbundle.connect.model.Project;
import com.connectbundle.connect.model.ProjectApplication;
import com.connectbundle.connect.model.ProjectTeamMember;
import com.connectbundle.connect.model.ProjectVerification;
import com.connectbundle.connect.model.User.User;
import com.connectbundle.connect.model.enums.ProjectApplicationStatus;
import com.connectbundle.connect.model.enums.ProjectTeamRole;
import com.connectbundle.connect.repository.ProjectRepository;
import com.connectbundle.connect.service.UserService.UserServiceResponse;

import lombok.Getter;

@Transactional
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final S3Service s3Service;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, S3Service s3Service,
                          UserService userService, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.s3Service = s3Service;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public ProjectServiceResponse<Project> getProjectByID(Long id) {
        try {
            Optional<Project> optionalProject = projectRepository.findById(id);
            if (optionalProject.isPresent()) {
                Project project = optionalProject.get();
                return new ProjectServiceResponse<>(true, "Project found", project);
            } else {
                return new ProjectServiceResponse<>(false, "Project not found", null);
            }
        } catch (Exception e) {
            return new ProjectServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public ResponseEntity<BaseResponse<ProjectResponseDTO> >createProject(CreateProjectDTO projectDTO) {
        var verificationFaculty = userService.getUserByID(projectDTO.getVerificationFaculty());
        var facultyMentor = userService.getUserByID(projectDTO.getFacultyMentor());
        var ownerUser = userService.getUserByID(projectDTO.getOwnerId());
        if (!ownerUser.isSuccess()) {
            return  BaseResponse.error( "User Not Found", HttpStatus.NOT_FOUND);
        }
        Project newProject = modelMapper.map(projectDTO, Project.class);
        newProject.setFacultyMentor(facultyMentor.getData());
        newProject.setVerificationFaculty(verificationFaculty.getData());
        newProject.setOwnerId(ownerUser.getData());
        Project createdProject = projectRepository.save(newProject);
        ProjectResponseDTO responseDTO = modelMapper.map(createdProject, ProjectResponseDTO.class);
        //responseDTO.setOwnerId(createdProject.getOwnerId());
        return  BaseResponse.success(responseDTO, "Project created", 1);
    }

    public ProjectServiceResponse<Void> uploadProjectImage(MultipartFile file, Project project) {
        try {
            String uniqueFileName = s3Service.uploadFile(file, "ProjectImage");
            project.setProjectImage("https://connect-beta-2" + ".s3.amazonaws.com/" + uniqueFileName);
            projectRepository.save(project);
            return new ProjectServiceResponse<>(true, "File Uploaded", null);
        } catch (Exception e) {
            return new ProjectServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public ResponseEntity<BaseResponse<List<ProjectResponseDTO>>> getAllProjects() {
        List<Project> allProjects = projectRepository.findAll();
        List<ProjectResponseDTO> projectDTOs = allProjects.stream().map(project -> modelMapper.map(project, ProjectResponseDTO.class)).toList();
        return allProjects.isEmpty()
                ? BaseResponse.error("Projects Not Found", HttpStatus.NOT_FOUND)
                : BaseResponse.success(projectDTOs, "Projects Fetched Successfully",allProjects.size());
    }


    public ProjectServiceResponse<Project> deleteProject(Long id) {
        try {
            Optional<Project> optionalProject = projectRepository.findById(id);
            if (optionalProject.isEmpty()) {
                return new ProjectServiceResponse<>(false, "Project Not Found", null);
            } else {
                Project project = optionalProject.get();
                Long projectId = project.getId();
                projectRepository.deleteById(projectId);
                return new ProjectServiceResponse<>(true, "Project Deleted Successfully", null);
            }
        } catch (Exception e) {
            return new ProjectServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public ResponseEntity<BaseResponse<List<ProjectResponseDTO>>> getProjectsByUser(Long userId) {
        Optional<User> user = userService.getUserByID(userId).getData() == null ? Optional.empty() : Optional.of(userService.getUserByID(userId).getData());

        if (user.isEmpty()) {
            return BaseResponse.error("User not found", HttpStatus.NOT_FOUND);
        }

        List<Project> userProjects = projectRepository.findByOwnerId(user.get());

        if (userProjects.isEmpty()) {
            return BaseResponse.error("No projects found for this user", HttpStatus.NOT_FOUND);
        }

        List<ProjectResponseDTO> projectDTOs = userProjects.stream()
                .map(project -> modelMapper.map(project, ProjectResponseDTO.class))
                .toList();

        return BaseResponse.success(projectDTOs, "Projects fetched successfully", projectDTOs.size());
    }

    public ResponseEntity<BaseResponse<ProjectResponseDTO>> updateProject (Long projectId, UpdateProjectDTO updateProjectDTO) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isEmpty()) {
            return BaseResponse.error("Project not", HttpStatus.NOT_FOUND);
        }
        Project project = optionalProject.get();
        if (updateProjectDTO.getProjectName() != null) {
            project.setProjectName(updateProjectDTO.getProjectName());
        }
        if (updateProjectDTO.getProjectDescription() != null) {
            project.setProjectDescription(updateProjectDTO.getProjectDescription());
        }
        if (updateProjectDTO.getPrerequisites() != null) {
            project.setPrerequisites(updateProjectDTO.getPrerequisites());
        }
        if (updateProjectDTO.getTechStack() != null) {
            project.setTechStack(updateProjectDTO.getTechStack());
        }
        if (updateProjectDTO.getTags() != null) {
            project.setTags(updateProjectDTO.getTags());
        }
        if (updateProjectDTO.getProjectDurationMonths() != null) {
            project.setProjectDurationMonths(updateProjectDTO.getProjectDurationMonths());
        }
        if (updateProjectDTO.getProjectLevel() != null) {
            project.setProjectLevel(updateProjectDTO.getProjectLevel());
        }
        if (updateProjectDTO.getProjectStatus() != null) {
            project.setProjectStatus(updateProjectDTO.getProjectStatus());
        }
        Project updatedProject = projectRepository.save(project);
        ProjectResponseDTO projectResponseDTO = modelMapper.map(updatedProject, ProjectResponseDTO.class);

        return BaseResponse.success(projectResponseDTO, "Project Updated", 1);
    }

    public ResponseEntity<BaseResponse<ProjectResponseDTO>>addUserToApplication(AddUserToApplicationDTO addUserToApplicationDTO) {
        UserServiceResponse<User> optionalUser = userService.getUserByID(addUserToApplicationDTO.getUserID());
        if (!optionalUser.isSuccess()) {
            return BaseResponse.error("User not found", HttpStatus.NOT_FOUND);
        }
        Optional<Project> optionalProject = projectRepository.findById(addUserToApplicationDTO.getProjectID());
        if (optionalProject.isEmpty()) {
            return BaseResponse.error("Project not found", HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.getData();
        Project project = optionalProject.get();
        ProjectApplication projectApplication = new ProjectApplication();
        projectApplication.setProject(project);
        projectApplication.setStudent(user);
        projectApplication.setStatus(ProjectApplicationStatus.APPLIED);
        projectApplication.setApplicationDate(LocalDate.now());
        project.getProjectApplications().add(projectApplication);
        Project updatedProject = projectRepository.save(project);
        ProjectResponseDTO projectResponseDTO = modelMapper.map(updatedProject, ProjectResponseDTO.class);
        return BaseResponse.success(projectResponseDTO, "Added user", 1);
    }

    public ResponseEntity<BaseResponse<ProjectResponseDTO>>addUserToProject(AddUserToApplicationDTO addUserToApplicationDTO) {
        UserServiceResponse<User> optionalUser = userService.getUserByID(addUserToApplicationDTO.getUserID());
        if (!optionalUser.isSuccess()) {
            return BaseResponse.error("User not found", HttpStatus.NOT_FOUND);
        }
        Optional<Project> optionalProject = projectRepository.findById(addUserToApplicationDTO.getProjectID());
        if (optionalProject.isEmpty()) {
            return BaseResponse.error("Project not found", HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.getData();
        Project project = optionalProject.get();
        ProjectTeamMember projectTeamMember = new ProjectTeamMember();
        projectTeamMember.setProject(project);
        projectTeamMember.setUser(user);
        projectTeamMember.setRole(ProjectTeamRole.TEAM_MEMBER);
        project.getProjectTeamMembers().add(projectTeamMember);
        Project updatedProject = projectRepository.save(project);
        ProjectResponseDTO projectResponseDTO = modelMapper.map(updatedProject, ProjectResponseDTO.class);
        return BaseResponse.success(projectResponseDTO, "Added user", 1);
    }


    // RESPONSE CLASS
    @Getter
    public static class ProjectServiceResponse<T> {
        private final boolean success;
        private final String message;
        private final T data;

        public ProjectServiceResponse(boolean success, String message, T data) {
            this.message = message;
            this.success = success;
            this.data = data;
        }
    }

}
