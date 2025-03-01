package com.connectbundle.connect.dto.CommentDTO;

import com.connectbundle.connect.dto.PostsDTO.PostAuthorDTO;

public class CommentResponseDTO {
    private Long id;
    private PostAuthorDTO author;
    private String content;
    private String timestamp;

}
