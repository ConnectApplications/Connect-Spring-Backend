package com.connectbundle.connect.dto.ProjectsDTO;

import com.connectbundle.connect.model.enums.ProjectLevelEnum;
import com.connectbundle.connect.model.enums.ProjectStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectDTO {
    @NotBlank(message = "Project name is required")
    private String projectName;
    @NotBlank(message = "Project description is required")
    private String projectDescription;

    @NotNull(message = "OwnerId is Required.")
    private String owner;

    private String prerequisites;

    private String facultyMentor;

    private String verificationFaculty;

    private List<String> techStack;

    private List<String> tags;

    private Integer projectDurationMonths;

    private ProjectLevelEnum projectLevel;

    private ProjectStatusEnum projectStatus;

    //private Integer maxTeamSize;

    private String projectRepo;
}