package com.connectbundle.connect.dto.ProjectsDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserToApplicationDTO {
    private Long userID;
    private Long ProjectID;
}
