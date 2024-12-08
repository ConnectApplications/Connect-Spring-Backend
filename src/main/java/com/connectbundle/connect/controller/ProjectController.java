package com.connectbundle.connect.controller;

import com.connectbundle.connect.model.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProjectController {
    private List<Project> projects = new ArrayList<Project>();
    @GetMapping("/projects")
    public List<Project> getAll(){
        return projects;
    }

    @PostMapping("/jobs")
    public String createJob(@RequestBody Project project){
        projects.add(project);
        return "Project Added Successfully";
    }
}
