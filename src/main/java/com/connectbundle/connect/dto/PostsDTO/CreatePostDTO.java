package com.connectbundle.connect.dto.PostsDTO;

import com.connectbundle.connect.model.enums.PostTypeEnum;
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
public class CreatePostDTO {
    @NotBlank(message = "Post Content is required")
    private String content;
    @NotNull(message = "Post Type is required")
    private PostTypeEnum type;
    private String visibility;
    private String[] tags;
    private String userName;
}
