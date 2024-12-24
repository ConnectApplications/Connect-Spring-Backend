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
import com.connectbundle.connect.model.Role;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.service.JWTService;
import com.connectbundle.connect.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;

    @PostMapping("/login")
    // Login User
    public ResponseEntity<BaseResponse<String>> loginUser (@RequestBody LoginRequest loginRequest) {
        Object[] check = userService.loginUser(loginRequest.getUsername(),loginRequest.getPassword());
        boolean isValidLogin = (boolean) check[0];
        String role = check[1].toString();
        if (isValidLogin) {
            String token = jwtService.generateToken(loginRequest.getUsername(),role);
            return BaseResponse.success(token, "Login Success", HttpStatus.OK, 0);
        }
        return BaseResponse.error("Incorrect username or password", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    // Register User
    public ResponseEntity<BaseResponse<User>> registerUser (@RequestBody User newUser) {
        try {
            User registeredUser = userService.registerUser(newUser);
            return BaseResponse.success(registeredUser, "User registered Successfully", HttpStatus.OK, 0);
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
