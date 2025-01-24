package com.connectbundle.connect.dto.AwardsDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAwardDTO {
    @NotBlank(message = "Recipient id cannot be empty")
    private Long recipientID;
    @NotBlank(message = "Award name cannot be empty")
    private String awardName;
    @NotBlank(message = "Year cannot be empty")
    private Integer year;
    @NotBlank(message = "Description cannot be empty")
    private String description;

}
