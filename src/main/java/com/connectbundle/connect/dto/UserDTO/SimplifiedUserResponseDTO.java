package com.connectbundle.connect.dto.UserDTO;

import com.connectbundle.connect.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimplifiedUserResponseDTO {

    private String username;
    private String name;
    private String profilePicture;
    private String headline;
    private Role role;
}
