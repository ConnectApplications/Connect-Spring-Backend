package com.connectbundle.connect.dto.EventsDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEventDTO {
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Event name cannot be empty")
    private String eventName;
    @NotBlank(message = "Location cannot be empty")
    private String location;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    @NotBlank(message = "Date cannot be empty")
    private String date;
}
