package com.connectbundle.connect.dto.UserDTO;

import com.connectbundle.connect.model.enums.Role;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    @NotBlank(message = "Username must not be empty")
    private String username;

    @NotBlank(message = "Email must not be empty")
    private String email;

    @NotBlank(message = "Password must not be empty")
    private String password;

    @NotBlank(message = "Name must not be empty")
    private String name;

    private String headline;  // Optional

    private String about;  // Optional

    private String location;  // Optional

    private String industry;  // Optional

    private String currentPosition;  // Optional

    private Role role;
}

