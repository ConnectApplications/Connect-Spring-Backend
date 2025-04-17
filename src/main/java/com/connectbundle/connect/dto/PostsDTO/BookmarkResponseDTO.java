package com.connectbundle.connect.dto.PostsDTO;

import com.connectbundle.connect.dto.UserDTO.BaseUserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkResponseDTO {
    private Long id;
    private BaseUserResponseDTO user;
}

