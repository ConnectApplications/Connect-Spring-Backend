package com.connectbundle.connect.dto.PostsDTO;

import com.connectbundle.connect.dto.CommentDTO.CommentResponseDTO;
import com.connectbundle.connect.model.enums.PostTypeEnum;
import com.connectbundle.connect.model.enums.PostVisibilityEnum;
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
public class PostResponseDTO {
    private String id;
    private PostTypeEnum type;
    private PostVisibilityEnum visibility;
    private String content;
    private LocalDateTime createdAt;
    private List<LikeResponseDTO> likes;
    private List<CommentResponseDTO> comments;
    private Boolean isLikedByUser;
    private Boolean isBookmarked;
    private PostAuthorDTO author;
    private String media;
    private List<String> tags;
    private List<CommentResponseDTO> commentList;


}
