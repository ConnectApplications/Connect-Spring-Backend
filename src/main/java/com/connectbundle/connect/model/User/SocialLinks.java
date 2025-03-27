package com.connectbundle.connect.model.User;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialLinks {
    private String github;
    private String linkedin;
    private String portfolio;
}
