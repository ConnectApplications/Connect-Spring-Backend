package com.connectbundle.connect.dto.PostsDTO;

import com.connectbundle.connect.model.enums.PostTypeEnum;
import com.connectbundle.connect.model.enums.PostVisibilityEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostDTO {

    private String content;
    private PostTypeEnum type;
    private PostVisibilityEnum visibility;
    private List<String> tags;
    private String media;
}
