package com.connectbundle.connect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.model.Project;
import com.connectbundle.connect.service.ProjectService;
import com.connectbundle.connect.service.ProjectService.ProjectServiceResponse;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/getProject/{id}")
    // Get a project by its id
    public ResponseEntity<BaseResponse<Project>> getProject(@PathVariable Long id) {
        try {
            ProjectServiceResponse<Project> projectServiceResponse = projectService.getProjectByID(id);
            boolean success = projectServiceResponse.isSuccess();
            if (success) {
                Project project = projectServiceResponse.getData();
                String message = projectServiceResponse.getMessage();
                return BaseResponse.success(project, message, HttpStatus.OK, 0);
            }
            return BaseResponse.error(projectServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createProject")
    // Create a new project
    public ResponseEntity<BaseResponse<Project>> createProject(@RequestBody Project project) {
        try {
            ProjectServiceResponse<Project> createdProject = projectService.createProject(project);
            boolean success = createdProject.isSuccess();
            String message = createdProject.getMessage();
            if (success) {
                return BaseResponse.success(createdProject.getData(), message, HttpStatus.OK, 0);
            } else {
                return BaseResponse.error(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uploadProjectImage/{id}")
    // Upload or update an image for the project with the given ID
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
                    return BaseResponse.success(null, uploadedImage.getMessage(), HttpStatus.OK, 0);
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

    @GetMapping("/getAllProjects")
    // Get a list of all the projects
    public ResponseEntity<BaseResponse<List<Project>>> getALlProjects() {
        try {
            ProjectServiceResponse<List<Project>> allProjects = projectService.getAllProjects();
            boolean success = allProjects.isSuccess();
            String message = allProjects.getMessage();
            if (success) {
                return BaseResponse.success(allProjects.getData(), message, HttpStatus.OK, 0);
            } else {
                return BaseResponse.error(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteProject/{id}")
    // Delete a project by its id
    public ResponseEntity<BaseResponse<Project>> deleteById(@PathVariable Long id) {
        try {
            ProjectServiceResponse<Project> deletedProject = projectService.deleteProject(id);
            boolean success = deletedProject.isSuccess();
            String message = deletedProject.getMessage();
            if (success) {
                return BaseResponse.success(deletedProject.getData(), message, HttpStatus.OK, 0);
            } else {
                return BaseResponse.error(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
