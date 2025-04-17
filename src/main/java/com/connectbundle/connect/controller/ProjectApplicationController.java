package com.connectbundle.connect.controller;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.ProjectApplicationDTO.CreateProjectApplicationDTO;
import com.connectbundle.connect.dto.ProjectApplicationDTO.ProcessProjectApplicationDTO;
import com.connectbundle.connect.dto.ProjectApplicationDTO.ProjectApplicationDTO;
import com.connectbundle.connect.dto.ProjectsDTO.ProjectResponseDTO;
import com.connectbundle.connect.service.ProjectApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project/applications")
public class ProjectApplicationController {

    @Autowired
    private ProjectApplicationService projectApplicationService;

    @Autowired
    public ProjectApplicationController(ProjectApplicationService projectApplicationService) {
        this.projectApplicationService = projectApplicationService;
    }

    @PostMapping
    @Operation(summary = "Apply to Project", description = "Submit an application to join a project or request mentorship")
    public ResponseEntity<BaseResponse<ProjectApplicationDTO>> applyToProject(@RequestBody CreateProjectApplicationDTO dto) {
        return projectApplicationService.applyToProject(dto);
    }

    @PostMapping("/{id}")
    @Operation(summary = "Accept Application", description = "Accept a project application and add the applicant as a project team member")
    public ResponseEntity<BaseResponse<ProjectResponseDTO>> acceptApplication(@RequestBody ProcessProjectApplicationDTO dto) {
        return projectApplicationService.processApplication(dto.getApplicationId(),dto.isAccept(), dto.getDescription());
    }
}