package com.connectbundle.connect.dto.AuthDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateOtpRequest {
    @NotBlank(message = "email must not be empty")
    private String email;
    @NotBlank(message = "otp must not be empty")
    private String otp;
}
