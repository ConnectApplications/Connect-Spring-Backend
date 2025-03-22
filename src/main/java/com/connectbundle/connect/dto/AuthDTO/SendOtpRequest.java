package com.connectbundle.connect.dto.AuthDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SendOtpRequest {
    @NotBlank(message = "Email must not be empty")
    public String email;
}
