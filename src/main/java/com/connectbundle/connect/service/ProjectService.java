package com.connectbundle.connect.service;

import com.connectbundle.connect.model.Project;
import com.connectbundle.connect.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;





    public void createProject(Project project){
        projectRepository.save(project);
    }


    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
