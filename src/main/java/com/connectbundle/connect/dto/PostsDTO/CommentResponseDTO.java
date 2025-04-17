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
public class CommentResponseDTO {
    private Long id;
    private String content;
    private BaseUserResponseDTO user;
}
