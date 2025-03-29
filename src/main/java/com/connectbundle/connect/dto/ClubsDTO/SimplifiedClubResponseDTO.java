package com.connectbundle.connect.dto.ClubsDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimplifiedClubResponseDTO {
    private Long id;
    private String name;
    private String description;

}