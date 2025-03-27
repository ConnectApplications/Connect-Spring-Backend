package com.connectbundle.connect.dto.AuthDTO;

import com.connectbundle.connect.dto.UserDTO.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private UserResponseDTO user;
    private String token;
}
