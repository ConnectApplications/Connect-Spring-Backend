package com.connectbundle.connect.dto.ProjectsDTO;

import com.connectbundle.connect.model.ProjectApplication;
import com.connectbundle.connect.model.ProjectTeamMember;
import com.connectbundle.connect.model.ProjectVerification;
import com.connectbundle.connect.model.enums.ProjectLevelEnum;
import com.connectbundle.connect.model.enums.ProjectStatusEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Long ownerId;

    private String prerequisites;

    private Long facultyMentor;

    private Long verificationFaculty;

    private String[] techStack;

    private String[] tags;

    private Integer projectDurationMonths;

    private ProjectLevelEnum projectLevel;

    private ProjectStatusEnum projectStatus;

    private ProjectApplication[] projectApplications;

    private ProjectTeamMember[] projectTeamMembers;

    private ProjectVerification[] projectVerifications;

    //private Integer maxTeamSize;

    private String projectRepo;
}