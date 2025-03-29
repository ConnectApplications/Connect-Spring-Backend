package com.connectbundle.connect.dto.ClubsDTO;

import com.connectbundle.connect.model.PlanOfAction;
import com.connectbundle.connect.model.enums.ClubJoinPolicy;
import com.connectbundle.connect.model.enums.ClubVisibility;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateClubDTO {
    @NotBlank(message = "ClubName cannot be blank")
    private String name;

    private String banner;
    private String logo;

    @NotBlank(message = "Description cannot be blank")
    private String description;
    private String department;
    private List<String> tags;
    private String advisor;

    private ClubVisibility visibility = ClubVisibility.PUBLIC;
    private ClubJoinPolicy joinPolicy = ClubJoinPolicy.OPEN;


    private PlanOfAction planOfAction;
}
