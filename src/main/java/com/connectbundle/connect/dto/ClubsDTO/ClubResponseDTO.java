package com.connectbundle.connect.dto.ClubsDTO;

import com.connectbundle.connect.dto.UserDTO.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClubResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String department;
    private String otherDetails;
    private int membersCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private UserResponseDTO clubHead;
    private UserResponseDTO advisor;

    private List<UserResponseDTO> officeBearers;
//    private List<UserResponseDTO> clubMembers;
//    private List<UserResponseDTO> events;
//    private PlanOfActionDTO planOfAction;

}
