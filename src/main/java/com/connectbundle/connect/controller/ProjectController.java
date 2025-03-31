package com.connectbundle.connect.controller;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.ProjectsDTO.CreateProjectDTO;
import com.connectbundle.connect.dto.ProjectsDTO.ProjectResponseDTO;
import com.connectbundle.connect.model.Project;
import com.connectbundle.connect.model.enums.ProjectLevelEnum;
import com.connectbundle.connect.model.enums.ProjectStatusEnum;
import com.connectbundle.connect.service.ProjectService;
import com.connectbundle.connect.service.ProjectService.ProjectServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@Tag(name = "Project", description = "Project Endpoints")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Project By ID", description = "Retrieve a project by its ID")
    public ResponseEntity<BaseResponse<Project>> getProject(@PathVariable Long id) {
        try {
            ProjectServiceResponse<Project> projectServiceResponse = projectService.getProjectByID(id);
            boolean success = projectServiceResponse.isSuccess();
            if (success) {
                Project project = projectServiceResponse.getData();
                String message = projectServiceResponse.getMessage();
                return BaseResponse.success(project, message, 1);
            }
            return BaseResponse.error(projectServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    @Operation(summary = "Create Project", description = "Create a new project")
    public ResponseEntity<BaseResponse<ProjectResponseDTO>> createProject(@Valid @RequestBody CreateProjectDTO projectDTO) {
        return projectService.createProject(projectDTO);
    }

    @PostMapping("/uploadProjectImage/{id}")
    @Operation(summary = "Upload Project Image", description = "Upload or update an image for the project with the given ID")
    public ResponseEntity<BaseResponse<Void>> uploadProjectImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable Long id) {
        try {
            ProjectServiceResponse<Project> projectServiceResponse = projectService.getProjectByID(id);
            boolean success = projectServiceResponse.isSuccess();
            if (success) {
                Project project = projectServiceResponse.getData();
                ProjectServiceResponse<Void> uploadedImage = projectService.uploadProjectImage(file, project);
                if (uploadedImage.isSuccess()) {
                    return BaseResponse.success(null, uploadedImage.getMessage(), null);
                } else {
                    return BaseResponse.error(uploadedImage.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return BaseResponse.error(projectServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    @Operation(summary = "Get All Projects", description = "Retrieve a list of all projects")
    public ResponseEntity<BaseResponse<List<ProjectResponseDTO>>> getAllProjects() {
        return projectService.getAllProjects();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Project", description = "Delete a project by its ID")
    public ResponseEntity<BaseResponse<Void>> deleteById(@PathVariable Long id) {
        return projectService.deleteProject(id);
    }

//    @GetMapping("/getProjectsByUser/{userId}")
//    @Operation(summary = "Get Projects By User ID", description = "Retrieve all projects owned by a user")
//    public ResponseEntity<BaseResponse<List<ProjectResponseDTO>>> getProjectsByUser(@PathVariable Long userId) {
//        return projectService.getProjectsByUser(userId);
//    }

//    @PatchMapping("/updateProjectById/{projectId}")
//    @Operation(summary = "Update project by Project ID", description = "Updates a project based on the project id")
//    public ResponseEntity<BaseResponse<ProjectResponseDTO>> updateById(@Valid @RequestBody UpdateProjectDTO updateProjectDTO, @PathVariable Long projectId) {
//        return projectService.updateProject(projectId, updateProjectDTO);
//    }

    @GetMapping("/search")
    @Operation(summary = "Search Projects", description = "Search projects by optional filters like username, tags, project status, project level, and project name")
    public ResponseEntity<BaseResponse<List<ProjectResponseDTO>>> searchProjects(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) ProjectStatusEnum projectStatus,
            @RequestParam(required = false) ProjectLevelEnum projectLevel,
            @RequestParam(required = false) String projectName
    ) {
        return projectService.searchProjects(username, tags, projectStatus, projectLevel, projectName);
    }
//    @PatchMapping("/addUserToProjectApplication")
//    @Operation(summary = "Add a user to project application", description = "Add a user to project application")
//    public ResponseEntity<BaseResponse<ProjectResponseDTO>> addUserToApplication(@Valid @RequestBody AddUserToApplicationDTO addUserToApplicationDTO) {
//        return projectService.addUserToApplication(addUserToApplicationDTO);
//    }
//
//    @PatchMapping("/addUserToProjectMember")
//    @Operation(summary = "Add a user to project", description = "Add a user to project")
//    public ResponseEntity<BaseResponse<ProjectResponseDTO>> addUserToProject(@Valid @RequestBody AddUserToApplicationDTO addUserToApplicationDTO) {
//        return projectService.addUserToProject(addUserToApplicationDTO);
//    }


}
