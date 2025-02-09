package com.connectbundle.connect.dto.ClubsDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClubDTO {
    @NotBlank(message = "Club name cannot be empty")
    private String clubName;
    @NotBlank(message = "Activities cannot be empty")
    private String activities;

}
