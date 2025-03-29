package com.connectbundle.connect.dto.EventsDTO;

import com.connectbundle.connect.dto.ClubsDTO.SimplifiedClubResponseDTO;
import com.connectbundle.connect.model.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime date;
    private EventType type;
    private String location;
    private String registrationLink;
    private String natureOfEvent;
    private List<String> theme;
    private String fundingAgency;
    private String chiefGuest;
    private List<String> otherSpeakers;
    private Integer participantsCount;
    private Boolean isCompleted;
    private Boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private SimplifiedClubResponseDTO club;
}

