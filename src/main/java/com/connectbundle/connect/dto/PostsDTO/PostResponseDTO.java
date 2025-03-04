package com.connectbundle.connect.dto.PostsDTO;

import com.connectbundle.connect.dto.CommentDTO.CommentResponseDTO;

public class PostResponseDTO {
    private Long id;
    private String type;
    private String content;
    private String timestamp;
    private Long likes;
    private Long comments;
    private Boolean isLiked;
    private Boolean IsBookmarked;
    private PostAuthorDTO author;
    private PostMediaDTO media;
    private String[] tags;
    private CommentResponseDTO[] commentList;


}
