package com.connectbundle.connect.dto.ClubsDTO;

import com.connectbundle.connect.dto.EventsDTO.EventResponseDTO;
import com.connectbundle.connect.dto.UserDTO.SimplifiedUserResponseDTO;
import com.connectbundle.connect.model.enums.ClubRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClubResponseDTO {
    private Long id;
    private String name;
    private String banner;
    private String logo;
    private String description;
    private String department;

    private List<String> tags;
    private boolean isActive;

    private SimplifiedUserResponseDTO createdBy;
    private SimplifiedUserResponseDTO advisor;


    private List<ClubMemberResponseDTO> members;
    private List<EventResponseDTO> events;
    private PlanOfActionDTO planOfAction;
    
    // Authenticated user's membership details
    private boolean userMember;
    private ClubRoleEnum userRole;
    private boolean canEdit;
}
