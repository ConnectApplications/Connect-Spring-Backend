package com.connectbundle.connect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.UserDTO.AddUserSkillDTO;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.service.UserService;
import com.connectbundle.connect.service.UserService.UserServiceResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController()
@RequestMapping("/api/user")
@Tag(name = "User", description = "User Endpoints")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUser/{username}")
    @Operation(summary = "Get User By Username", description = "Fetch user by username")
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

    @PostMapping("/uploadProfilePicture/{username}")
    @Operation(summary = "Upload Profile Picture", description = "Update or upload user's profile picture by their username")
    public ResponseEntity<BaseResponse<Void>> uploadUserProfilePicture(
            @RequestParam("file") MultipartFile file,
            @PathVariable String username) {
        try {
            UserServiceResponse<User> optionalUser = userService.getUserByUsername(username);
            if (optionalUser.isSuccess()) {
                User user = optionalUser.getData();
                UserServiceResponse<Void> uploadedPicture = userService.uploadUserImage(file, user);
                if (uploadedPicture.isSuccess()) {
                    return BaseResponse.success(null, uploadedPicture.getMessage(), HttpStatus.OK, 0);
                } else {
                    return BaseResponse.error(uploadedPicture.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return BaseResponse.error(optionalUser.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteUser/{username}")
    @Operation(summary = "Delete User", description = "Delete user by username")
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

    @PostMapping("/addSkill/{username}")
    @Operation(summary = "Add Skill", description = "Add a skill to a user. Skill should be sent in the request body")
    public ResponseEntity<BaseResponse<Void>> addSkillToUser(
            @PathVariable String username,
            @RequestBody AddUserSkillDTO skill) {
        try {
            UserServiceResponse<Void> addedSkill = userService.addSkillToUser(username, skill);
            if (addedSkill.isSuccess()) {
                return BaseResponse.success(null, addedSkill.getMessage(), HttpStatus.OK, 0);
            } else {
                return BaseResponse.error(addedSkill.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
