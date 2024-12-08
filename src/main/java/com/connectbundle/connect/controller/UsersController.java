//package com.connectbundle.connect.controller;
//
//import com.connectbundle.connect.model.Users;
//import com.connectbundle.connect.service.UsersService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/users")
//public class UsersController {
//    @Autowired
//    private UsersService usersService;
//
//    // Get all users
//    @GetMapping
//    public ResponseEntity<List<Users>> getAllUsers() {
//        return ResponseEntity.ok(usersService.getAllUsers());
//    }
//
//    // Add a new user
//    @PostMapping
//    public ResponseEntity<Users> addUser(@RequestBody Users user) {
//        return ResponseEntity.ok(usersService.addUser(user));
//    }
//
//    // Get user by username
//    @GetMapping("/{username}")
//    public ResponseEntity<Users> getUserByUsername(@PathVariable String username) {
//        Users user = usersService.getUserByUsername(username);
//        if (user != null) {
//            return ResponseEntity.ok(user);
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    // Delete a user by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
//        usersService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
//}
