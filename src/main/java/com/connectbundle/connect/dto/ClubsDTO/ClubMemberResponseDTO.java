package com.connectbundle.connect.dto.ClubsDTO;

import com.connectbundle.connect.model.enums.ClubRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubMemberResponseDTO {
    private Long clubId;
    private String clubName;
    private String rollNo;
    private ClubRoleEnum role;
}