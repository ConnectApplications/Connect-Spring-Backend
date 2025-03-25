package com.connectbundle.connect.dto.PostsDTO;

import com.connectbundle.connect.dto.CommentDTO.CommentResponseDTO;
import com.connectbundle.connect.model.enums.PostVisibilityEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {
    private String id;
    private String type;
    private String visibility;
    private String content;
    private String timestamp;
    private Long likes;
    private Long comments;
    private Boolean isLiked;
    private Boolean isBookmarked;
    private PostAuthorDTO author;
    private PostMediaDTO media;
    private List<String> tags;
    private List<CommentResponseDTO> commentList;


}
