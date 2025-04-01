package com.connectbundle.connect.dto.PostsDTO;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostMediaDTO {
    private String type;
    private String url;
}
