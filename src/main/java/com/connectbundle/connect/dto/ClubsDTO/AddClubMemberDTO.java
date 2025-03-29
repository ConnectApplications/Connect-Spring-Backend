package com.connectbundle.connect.dto.ClubsDTO;

import com.connectbundle.connect.model.enums.ClubRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddClubMemberDTO {
    @NotBlank(message = "Club id cannot be empty")
    private Long clubId;
    @NotBlank(message = "Username cannot be empty")
    private String username;
    private String rollNo;
    @NotNull(message = "Role cannot be empty")
    private ClubRoleEnum role;
}
