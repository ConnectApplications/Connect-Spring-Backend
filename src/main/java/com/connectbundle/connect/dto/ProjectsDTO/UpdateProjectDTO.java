package com.connectbundle.connect.dto.ProjectsDTO;

import com.connectbundle.connect.model.ProjectApplication;
import com.connectbundle.connect.model.ProjectTeamMember;
import com.connectbundle.connect.model.ProjectVerification;
import com.connectbundle.connect.model.enums.ProjectLevelEnum;
import com.connectbundle.connect.model.enums.ProjectStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProjectDTO {
    private String projectName;
    private String projectDescription;

    private String prerequisites;

    private String techStack;

    private String tags;

    private Integer projectDurationMonths;

    private ProjectLevelEnum projectLevel;

    private ProjectStatusEnum projectStatus;

    //private Integer maxTeamSize;

    private String projectRepo;
}
