package com.connectbundle.connect.dto.PostsDTO;

import com.connectbundle.connect.model.enums.PostType;
import com.connectbundle.connect.model.enums.PostVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Image is required")
    private String image;

    @NotNull(message = "Visibility is required")
    private PostVisibility visibility;

    @NotBlank(message = "Creation date is required")
    private String createdAt;

    @NotNull(message = "Likes count is required")
    private Integer likes;

    private Integer comments;
    private Integer reposts;

    @NotNull(message = "Post type is required")
    private PostType type;
}
