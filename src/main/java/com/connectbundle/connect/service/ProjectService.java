package com.connectbundle.connect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectbundle.connect.model.Project;
import com.connectbundle.connect.repository.ProjectRepository;

import lombok.Getter;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    // REPONSE CLASS
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

    public ProjectServiceResponse<Project> createProject(Project project) {
        try {
            Project createdProject = projectRepository.save(project);
            return new ProjectServiceResponse<>(true, "Project created", createdProject);
        } catch (Exception e) {
            return new ProjectServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public ProjectServiceResponse<List<Project>> getAllProjects() {
        try {
            List<Project> allProjects = projectRepository.findAll();
            if (allProjects.isEmpty()) {
                return new ProjectServiceResponse<>(false, "No Projects Found", null);
            } else {
                return new ProjectServiceResponse<>(true, "Projects Fetched Successfully", allProjects);
            }
        } catch (Exception e) {
            return new ProjectServiceResponse<>(false, e.getMessage(), null);
        }
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

}
