package com.connectbundle.connect.dto.UserDTO;

import com.connectbundle.connect.model.enums.Role;
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
public class CreateUserDTO {
    @NotBlank(message = "Username must not be empty")
    private String username;
    @NotBlank(message = "Email must not be empty")
    private String email;
    @NotBlank(message = "Password must not be empty")
    private String password;
    @NotBlank(message = "FirstName must not be empty")
    private String firstName;
    @NotBlank(message = "LastName must not be empty")
    private String lastName;
    @NotBlank(message = "Headline must not be empty")
    private String headline;
    @NotBlank(message = "About must not be empty")
    private String about;
    @NotBlank(message = "Location must not be empty")
    private String location;
    @NotBlank(message = "Industry must not be empty")
    private String industry;
    @NotBlank(message = "CurrentPosition must not be empty")
    private String currentPosition;
    @NotNull(message = "Role must not be empty")
    private Role role;
}
