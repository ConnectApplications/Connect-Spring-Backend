//package com.connectbundle.connect.service;
//
//import com.connectbundle.connect.model.Users;
//import com.connectbundle.connect.repository.UsersRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UsersService {
//    @Autowired
//    private UsersRepository usersRepository;
//
//    // Get all users
//    public List<Users> getAllUsers() {
//        return usersRepository.findAll();
//    }
//
//    // Add a new user
//    public Users addUser(Users user) {
//        return usersRepository.save(user);
//    }
//
//    // Get user by username
//    public Users getUserByUsername(String username) {
//        return usersRepository.findByUsername(username);
//    }
//
//    // Delete a user by ID
//    public void deleteUser(String id) {
//        usersRepository.deleteById(id);
//    }
//}