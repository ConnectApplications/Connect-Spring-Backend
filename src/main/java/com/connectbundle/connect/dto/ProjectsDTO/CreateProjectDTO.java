package com.connectbundle.connect.dto.ProjectsDTO;

import com.connectbundle.connect.model.enums.ProjectLevelEnum;
import com.connectbundle.connect.model.enums.ProjectStatusEnum;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectDTO {
    @NotBlank(message = "Project name is required")
    private String projectName;
    @NotBlank(message = "Project description is required")
    private String projectDescription;
    @NotBlank(message = "Prerequisites are required")
    private String prerequisites;
    @NotBlank(message = "Faculty mentor ID is required")
    private String facultyMentorUsername;
    @NotBlank(message = "Verification faculty ID is required")
    private String verificationFacultyUsername;
    @NotBlank(message = "Tech stack is required")
    private String techStack;
    @NotBlank(message = "Tags are required")
    private String tags;
    @NotBlank(message = "Project duration in months is required")
    private Integer projectDurationMonths;
    @NotBlank(message = "Project level is required")
    private ProjectLevelEnum projectLevel;
    @NotBlank(message = "Project status is required")
    private ProjectStatusEnum projectStatus;
    @NotBlank(message = "Max team size is required")
    private Integer maxTeamSize;
    @NotBlank(message = "Project repo is required")
    private String projectRepo;
}