package com.connectbundle.connect.dto.ClubsDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveClubMemberDTO {
    @NotBlank(message = "Club id cannot be empty")
    private Long clubId;
    @NotBlank(message = "Username cannot be empty")
    private String username;
}
