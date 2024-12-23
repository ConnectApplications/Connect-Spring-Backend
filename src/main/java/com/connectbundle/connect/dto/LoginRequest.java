package com.connectbundle.connect.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    //TODO : ADD VALIDATION
    private String username;
    private String password;
}
