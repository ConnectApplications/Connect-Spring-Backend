package com.connectbundle.connect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectbundle.connect.dto.AuthDTO.LoginRequest;
import com.connectbundle.connect.dto.AuthDTO.SendOtpRequest;
import com.connectbundle.connect.dto.AuthDTO.ValidateOtpRequest;
import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.UserDTO.CreateUserDTO;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.service.EmailService;
import com.connectbundle.connect.service.EmailService.EmailServiceResponse;
import com.connectbundle.connect.service.JWTService;
import com.connectbundle.connect.service.UserService;
import com.connectbundle.connect.service.UserService.UserServiceResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Authentication Endpoints")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;

    @Autowired
    EmailService emailService;

    @PostMapping("/login")
    @Operation(summary = "Login User", description = "Authenticate user and return JWT token")
    public ResponseEntity<BaseResponse<String>> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        UserServiceResponse<String> check = userService.loginUser(loginRequest.getUsername(),
                loginRequest.getPassword());
        boolean isValidLogin = check.isSuccess();
        String role = check.getData(); // GET ROLE
        if (isValidLogin) {
            String token = jwtService.generateToken(loginRequest.getUsername(), role);
            return BaseResponse.success(token, "Login Success", 1);
        }
        return BaseResponse.error("Incorrect username or password", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    @Operation(summary = "Register User", description = "Register a new user")
    // TODO : EXTRACT IMAGE TO USE FOR PFP AND SAVE
    public ResponseEntity<BaseResponse<User>> registerUser(@Valid @RequestBody CreateUserDTO newUser) {
        try {
            User registeredUser = userService.registerUser(newUser);
            return BaseResponse.success(registeredUser, "User registered Successfully", 1);
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/send-otp")
    @Operation(summary = "Send OTP", description = "Send an OTP to the user's email")
    public ResponseEntity<BaseResponse<String>> sendOtp(@Valid @RequestBody SendOtpRequest sendOtpRequest) {
        try {
            EmailServiceResponse sendOtpResponse = emailService.sendOtp(sendOtpRequest.getEmail());
            boolean success = sendOtpResponse.isSuccess();
            String message = sendOtpResponse.getMessage();
            if (success) {
                return BaseResponse.success("", message, 1);
            } else {
                return BaseResponse.error(message, HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/validate-otp")
    @Operation(summary = "Validate OTP", description = "Validate the OTP sent to the user's email")
    public ResponseEntity<BaseResponse<String>> validateOtp(@Valid @RequestBody ValidateOtpRequest validateOtpRequest) {
        try {
            EmailServiceResponse validationResponse = emailService.validateOtp(validateOtpRequest.getEmail(),
                    Integer.parseInt(validateOtpRequest.getOtp()));
            boolean success = validationResponse.isSuccess();
            String message = validationResponse.getMessage();
            if (success) {
                return BaseResponse.success("", message, 1);
            } else {
                return BaseResponse.error(message, HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
