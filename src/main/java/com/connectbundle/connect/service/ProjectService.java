package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.ProjectsDTO.AddProjectMemberDTO;
import com.connectbundle.connect.dto.ProjectsDTO.CreateProjectDTO;
import com.connectbundle.connect.dto.ProjectsDTO.ProjectResponseDTO;
import com.connectbundle.connect.dto.ProjectsDTO.ProjectTeamMemberDTO;
import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.model.Project;
import com.connectbundle.connect.model.ProjectTeamMember;
import com.connectbundle.connect.model.User.User;
import com.connectbundle.connect.model.enums.ProjectLevelEnum;
import com.connectbundle.connect.model.enums.ProjectStatusEnum;
import com.connectbundle.connect.model.enums.ProjectTeamRole;
import com.connectbundle.connect.repository.ProjectRepository;
import com.connectbundle.connect.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<BaseResponse<ProjectResponseDTO>> getProjectByID(Long id) {
        Project Project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        ProjectResponseDTO responseDTO = modelMapper.map(Project, ProjectResponseDTO.class);

        return BaseResponse.success(responseDTO, "Project Fetched Successfully", 1);

    }

    public ResponseEntity<BaseResponse<ProjectResponseDTO> >createProject(CreateProjectDTO projectDTO) {

        User user = userRepository.findByUsername(projectDTO.getOwner())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username", projectDTO.getOwner()));
        User facultyMentor = null;
        if (projectDTO.getFacultyMentor() != null && !projectDTO.getFacultyMentor().trim().isEmpty()) {
            facultyMentor = userRepository.findByUsername(projectDTO.getFacultyMentor())
                    .orElseThrow(() -> new ResourceNotFoundException("Faculty", "Username", projectDTO.getFacultyMentor()));
        }

        User verificationFaculty = null;
        if (projectDTO.getVerificationFaculty() != null && !projectDTO.getVerificationFaculty().trim().isEmpty()) {
            verificationFaculty = userRepository.findByUsername(projectDTO.getVerificationFaculty())
                    .orElseThrow(() -> new ResourceNotFoundException("Faculty", "Username", projectDTO.getVerificationFaculty()));
        }
        Project newProject = modelMapper.map(projectDTO, Project.class);
        newProject.setFacultyMentor(facultyMentor);
        newProject.setVerificationFaculty(verificationFaculty);
        newProject.setOwner(user);
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


    public ResponseEntity<BaseResponse<Void>> deleteProject(Long id) {
        Project Project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        return BaseResponse.successMessage("Event deleted");

    }

//    public ResponseEntity<BaseResponse<List<ProjectResponseDTO>>> getProjectsByUser(Long userId) {
//        Optional<User> user = userService.getUserByID(userId).getData() == null ? Optional.empty() : Optional.of(userService.getUserByID(userId).getData());
//
//        if (user.isEmpty()) {
//            return BaseResponse.error("User not found", HttpStatus.NOT_FOUND);
//        }
//
//        List<Project> userProjects = projectRepository.findByOwnerId(user.get());
//
//        if (userProjects.isEmpty()) {
//            return BaseResponse.error("No projects found for this user", HttpStatus.NOT_FOUND);
//        }
//
//        List<ProjectResponseDTO> projectDTOs = userProjects.stream()
//                .map(project -> modelMapper.map(project, ProjectResponseDTO.class))
//                .toList();
//
//        return BaseResponse.success(projectDTOs, "Projects fetched successfully", projectDTOs.size());
//    }

//    public ResponseEntity<BaseResponse<ProjectResponseDTO>> updateProject (Long projectId, UpdateProjectDTO updateProjectDTO) {
//        Optional<Project> optionalProject = projectRepository.findById(projectId);
//        if (optionalProject.isEmpty()) {
//            return BaseResponse.error("Project not", HttpStatus.NOT_FOUND);
//        }
//        Project project = optionalProject.get();
//        if (updateProjectDTO.getProjectName() != null) {
//            project.setProjectName(updateProjectDTO.getProjectName());
//        }
//        if (updateProjectDTO.getProjectDescription() != null) {
//            project.setProjectDescription(updateProjectDTO.getProjectDescription());
//        }
//        if (updateProjectDTO.getPrerequisites() != null) {
//            project.setPrerequisites(updateProjectDTO.getPrerequisites());
//        }
//        if (updateProjectDTO.getTechStack() != null) {
//            project.setTechStack(updateProjectDTO.getTechStack());
//        }
//        if (updateProjectDTO.getTags() != null) {
//            project.setTags(updateProjectDTO.getTags());
//        }
//        if (updateProjectDTO.getProjectDurationMonths() != null) {
//            project.setProjectDurationMonths(updateProjectDTO.getProjectDurationMonths());
//        }
//        if (updateProjectDTO.getProjectLevel() != null) {
//            project.setProjectLevel(updateProjectDTO.getProjectLevel());
//        }
//        if (updateProjectDTO.getProjectStatus() != null) {
//            project.setProjectStatus(updateProjectDTO.getProjectStatus());
//        }
//        Project updatedProject = projectRepository.save(project);
//        ProjectResponseDTO projectResponseDTO = modelMapper.map(updatedProject, ProjectResponseDTO.class);
//
//        return BaseResponse.success(projectResponseDTO, "Project Updated", 1);
//    }

//    public ResponseEntity<BaseResponse<ProjectResponseDTO>>addUserToApplication(AddUserToApplicationDTO addUserToApplicationDTO) {
//        UserServiceResponse<User> optionalUser = userService.getUserByID(addUserToApplicationDTO.getUserID());
//        if (!optionalUser.isSuccess()) {
//            return BaseResponse.error("User not found", HttpStatus.NOT_FOUND);
//        }
//        Optional<Project> optionalProject = projectRepository.findById(addUserToApplicationDTO.getProjectID());
//        if (optionalProject.isEmpty()) {
//            return BaseResponse.error("Project not found", HttpStatus.NOT_FOUND);
//        }
//        User user = optionalUser.getData();
//        Project project = optionalProject.get();
//        ProjectApplication projectApplication = new ProjectApplication();
//        projectApplication.setProject(project);
//        projectApplication.setStudent(user);
//        projectApplication.setStatus(ProjectApplicationStatus.APPLIED);
//        projectApplication.setApplicationDate(LocalDate.now());
//        project.getProjectApplications().add(projectApplication);
//        Project updatedProject = projectRepository.save(project);
//        ProjectResponseDTO projectResponseDTO = modelMapper.map(updatedProject, ProjectResponseDTO.class);
//        return BaseResponse.success(projectResponseDTO, "Added user", 1);
//    }
//
//    public ResponseEntity<BaseResponse<ProjectResponseDTO>>addUserToProject(AddUserToApplicationDTO addUserToApplicationDTO) {
//        UserServiceResponse<User> optionalUser = userService.getUserByID(addUserToApplicationDTO.getUserID());
//        if (!optionalUser.isSuccess()) {
//            return BaseResponse.error("User not found", HttpStatus.NOT_FOUND);
//        }
//        Optional<Project> optionalProject = projectRepository.findById(addUserToApplicationDTO.getProjectID());
//        if (optionalProject.isEmpty()) {
//            return BaseResponse.error("Project not found", HttpStatus.NOT_FOUND);
//        }
//        User user = optionalUser.getData();
//        Project project = optionalProject.get();
//        ProjectTeamMember projectTeamMember = new ProjectTeamMember();
//        projectTeamMember.setProject(project);
//        projectTeamMember.setUser(user);
//        projectTeamMember.setRole(ProjectTeamRole.TEAM_MEMBER);
//        project.getProjectTeamMembers().add(projectTeamMember);
//        Project updatedProject = projectRepository.save(project);
//        ProjectResponseDTO projectResponseDTO = modelMapper.map(updatedProject, ProjectResponseDTO.class);
//        return BaseResponse.success(projectResponseDTO, "Added user", 1);
//    }

    public ResponseEntity<BaseResponse<List<ProjectResponseDTO>>> searchProjects(
            String username,
            List<String> tags,
            ProjectStatusEnum projectStatus,
            ProjectLevelEnum projectLevel,
            String projectName
    ) {
        Specification<Project> spec = (Specification<Project>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (username != null && !username.isEmpty()) {
                // Filter by owner's username
                predicateList.add(cb.equal(root.get("owner").get("username"), username));
            }
            if (tags != null && !tags.isEmpty()) {
                // Join the tags collection and filter using the provided tags
                predicateList.add(root.join("tags").in(tags));
            }
            if (projectStatus != null) {
                predicateList.add(cb.equal(root.get("projectStatus"), projectStatus));
            }
            if (projectLevel != null) {
                predicateList.add(cb.equal(root.get("projectLevel"), projectLevel));
            }
            if (projectName != null && !projectName.isEmpty()) {
                predicateList.add(cb.like(cb.lower(root.get("projectName")), "%" + projectName.toLowerCase() + "%"));
            }
            return cb.and(predicateList.toArray(new Predicate[0]));
        };

        List<Project> projects = projectRepository.findAll(spec);

        if (projects.isEmpty()) {
            return BaseResponse.success(null,"No projects found with given filters",0);
        }

        List<ProjectResponseDTO> responseDTOs = projects.stream()
                .map(project -> modelMapper.map(project, ProjectResponseDTO.class))
                .toList();

        return BaseResponse.success(responseDTOs, "Projects fetched successfully", responseDTOs.size());
    }

    public ResponseEntity<BaseResponse<ProjectResponseDTO>> addProjectMember(AddProjectMemberDTO dto) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project", "ID", dto.getProjectId()));
        User user = userRepository.findByUsername(dto.getUserName())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username",dto.getUserName()));

        boolean alreadyMember = project.getProjectTeamMembers()
                .stream()
                .anyMatch(ptm -> ptm.getUser().getId().equals(user.getId()));
        if (alreadyMember) {
            return BaseResponse.error("User is already a part of the project", HttpStatus.BAD_REQUEST);
        }

        ProjectTeamRole role = dto.getRole() != null ? dto.getRole() : ProjectTeamRole.TEAM_MEMBER;

        ProjectTeamMember teamMember = new ProjectTeamMember();
        teamMember.setProject(project);
        teamMember.setUser(user);
        teamMember.setRole(role);
        project.getProjectTeamMembers().add(teamMember);
        Project updatedProject = projectRepository.save(project);
        ProjectResponseDTO responseDTO = modelMapper.map(updatedProject, ProjectResponseDTO.class);

        List<ProjectTeamMemberDTO> teamMembers = updatedProject.getProjectTeamMembers()
                .stream()
                .map(ptm -> modelMapper.map(ptm, ProjectTeamMemberDTO.class))
                .collect(Collectors.toList());
        responseDTO.setProjectTeamMembers(teamMembers);
        return BaseResponse.success(responseDTO, "Project member added successfully", 1);
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
