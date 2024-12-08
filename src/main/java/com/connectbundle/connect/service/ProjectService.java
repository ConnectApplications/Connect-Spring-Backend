package com.connectbundle.connect.service;

import com.connectbundle.connect.model.Project;
import com.connectbundle.connect.repository.ProjectRepository;

import java.util.List;

public class ProjectService {
    ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    public void createProject(Project project){
        projectRepository.save(project);
    }



}
