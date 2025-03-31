package com.connectbundle.connect.dto.ProjectsDTO;

import com.connectbundle.connect.dto.UserDTO.BaseUserResponseDTO;
import com.connectbundle.connect.model.enums.ProjectLevelEnum;
import com.connectbundle.connect.model.enums.ProjectStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponseDTO {
    private Long id;
    private String projectName;
    private String projectDescription;
    private BaseUserResponseDTO owner;
    private String prerequisites;
    private List<String> techStack;
    private List<String> tags;
    private Integer projectDurationMonths;
    private ProjectLevelEnum projectLevel;
    private ProjectStatusEnum projectStatus;
    private String projectImage;
    private String projectRepo;
    private List<ProjectTeamMemberDTO> projectTeamMembers;
}