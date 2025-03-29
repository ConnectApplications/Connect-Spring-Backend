package com.connectbundle.connect.dto.ClubsDTO;

import com.connectbundle.connect.model.enums.ClubRoleEnum;
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
public class UpdateClubMemberDTO {
    @NotNull(message = "Club id cannot be empty")
    private Long clubId;
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotNull(message = "Role cannot be empty")
    private ClubRoleEnum newRole;
}

