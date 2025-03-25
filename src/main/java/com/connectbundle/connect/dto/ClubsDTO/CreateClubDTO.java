package com.connectbundle.connect.dto.ClubsDTO;

import com.connectbundle.connect.model.PlanOfAction;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClubDTO {
    @NotBlank(message = "ClubName cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;
    private String department;
    private String advisor;

    @NotBlank(message = "Club head cannot be blank ")
    private String clubHead;

    private String otherDetails;

    private PlanOfAction planOfAction;
}
