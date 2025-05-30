package com.connectbundle.connect.dto.PostsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostAuthorDTO {
    private String name;
    private String profilePicture;
    private String headline;
}
