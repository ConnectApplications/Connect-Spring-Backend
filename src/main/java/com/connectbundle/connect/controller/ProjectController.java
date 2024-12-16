package com.connectbundle.connect.controller;

import com.connectbundle.connect.configs.BaseResponse;
import com.connectbundle.connect.model.Project;
import com.connectbundle.connect.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController extends BaseApiController{

    @Autowired
    public ProjectService service;

    @GetMapping("/projects")
    public List<Project> getAll(){
        return service.getAllProjects();
    }

    @PostMapping("/projects")
    public void createJob(@RequestBody Project project){

        service.createProject(project);
    }

    @GetMapping("/projectser")
    public ResponseEntity<BaseResponse<List<String>>> getProjects() {
        List<String> projects = List.of("Project A", "Project B", "Project C");
        return BaseResponse.success(projects, "Projects fetched successfully", HttpStatus.OK,0);
    }
}
