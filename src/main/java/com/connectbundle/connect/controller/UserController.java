package com.connectbundle.connect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.service.UserService;
import com.connectbundle.connect.service.UserService.UserServiceResponse;

@RestController("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUser/{username}")
    public ResponseEntity<BaseResponse<User>> getUserByUsername(@PathVariable String username) {
        try {
            UserServiceResponse<User> user = userService.getUserByUsername(username);
            boolean success = user.isSuccess();
            String message = user.getMessage();
            if (success) {
                return BaseResponse.success(user.getData(), message, HttpStatus.OK, 0);
            } else {
                return BaseResponse.error(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteUser/{username}")
    public ResponseEntity<BaseResponse<Void>> deleteUserByUsername(@PathVariable String username) {
        try {
            UserServiceResponse<Void> deletedUser = userService.deleteUserByUsername(username);
            boolean success = deletedUser.isSuccess();
            String message = deletedUser.getMessage();
            if (success) {
                return BaseResponse.success(null, message, HttpStatus.OK, 0);
            } else {
                return BaseResponse.error(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
