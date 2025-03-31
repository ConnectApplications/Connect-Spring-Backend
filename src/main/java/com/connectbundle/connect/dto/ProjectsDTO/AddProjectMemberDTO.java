package com.connectbundle.connect.dto.ProjectsDTO;

import com.connectbundle.connect.model.enums.ProjectTeamRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProjectMemberDTO {
    private Long projectId;
    private String userName;
    private ProjectTeamRole role; // You can default this to TEAM_MEMBER if null in service
}
