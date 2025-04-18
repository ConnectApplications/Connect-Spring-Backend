package com.connectbundle.connect.dto.ClubsDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveClubMemberDTO {
    @NotNull(message = "Club id cannot be empty")
    private Long clubId;
    @NotBlank(message = "Username cannot be empty")
    private String username;
}
