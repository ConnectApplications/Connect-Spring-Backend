package com.connectbundle.connect.dto.ProjectsDTO;

import com.connectbundle.connect.dto.UserDTO.BaseUserResponseDTO;
import com.connectbundle.connect.model.enums.ProjectTeamRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTeamMemberDTO {

    private Long id;
    private BaseUserResponseDTO user;
    private ProjectTeamRole role;
}
