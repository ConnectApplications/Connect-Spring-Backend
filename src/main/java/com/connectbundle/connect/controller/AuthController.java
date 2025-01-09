package com.connectbundle.connect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.LoginRequest;
import com.connectbundle.connect.dto.SendOtpRequest;
import com.connectbundle.connect.dto.ValidateOtpRequest;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.service.EmailService;
import com.connectbundle.connect.service.EmailService.EmailServiceResponse;
import com.connectbundle.connect.service.JWTService;
import com.connectbundle.connect.service.UserService;
import com.connectbundle.connect.service.UserService.UserServiceResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;

    @Autowired
    EmailService emailService;

    @PostMapping("/login")
    // Login User
    public ResponseEntity<BaseResponse<String>> loginUser(@RequestBody LoginRequest loginRequest) {
        UserServiceResponse<String> check = userService.loginUser(loginRequest.getUsername(),
                loginRequest.getPassword());
        boolean isValidLogin = check.isSuccess();
        String role = check.getData(); // GET ROLE
        if (isValidLogin) {
            String token = jwtService.generateToken(loginRequest.getUsername(), role);
            return BaseResponse.success(token, "Login Success", HttpStatus.OK, 0);
        }
        return BaseResponse.error("Incorrect username or password", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    // Register User
    // TODO : EXTRACT IMAGE TO USE FOR PFP AND SAVE
    public ResponseEntity<BaseResponse<User>> registerUser(@RequestBody User newUser) {
        try {
            User registeredUser = userService.registerUser(newUser);
            return BaseResponse.success(registeredUser, "User registered Successfully", HttpStatus.OK, 0);
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/send-otp")
    // Send an OTP to the user
    public ResponseEntity<BaseResponse<String>> sendOtp(@RequestBody SendOtpRequest sendOtpRequest) {
        try {
            EmailServiceResponse sendOtpResponse = emailService.sendOtp(sendOtpRequest.getEmail());
            boolean success = sendOtpResponse.isSuccess();
            String message = sendOtpResponse.getMessage();
            if (success) {
                return BaseResponse.success("", message, HttpStatus.OK, 0);
            } else {
                return BaseResponse.error(message, HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/validate-otp")
    // Validate an OTP
    public ResponseEntity<BaseResponse<String>> validateOtp(@RequestBody ValidateOtpRequest validateOtpRequest) {
        try {
            EmailServiceResponse validationResponse = emailService.validateOtp(validateOtpRequest.getEmail(),
                    Integer.parseInt(validateOtpRequest.getOtp()));
            boolean success = validationResponse.isSuccess();
            String message = validationResponse.getMessage();
            if (success) {
                return BaseResponse.success("", message, HttpStatus.OK, 0);
            } else {
                return BaseResponse.error(message, HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
