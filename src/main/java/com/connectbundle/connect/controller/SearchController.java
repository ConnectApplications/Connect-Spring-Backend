package com.connectbundle.connect.controller;


import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.UserDTO.UserResponseDTO;
import com.connectbundle.connect.model.enums.Role;
import com.connectbundle.connect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<BaseResponse<List<UserResponseDTO>>> searchUsersByNameAndRole(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String name
    ) {
        Role roleEnum = null;
        if (role != null && !role.isEmpty()) {
            roleEnum = Role.valueOf(role.replace("ROLE_", "").toUpperCase());
        }

        return userService.searchByNameAndRole(roleEnum, name);
    }
}