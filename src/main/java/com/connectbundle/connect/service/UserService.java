package com.connectbundle.connect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.connectbundle.connect.model.Role;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.repository.UserRepository;

import lombok.Getter;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    // REPONSE CLASS
    @Getter
    public static class UserServiceResponse<T> {
        private final boolean success;
        private final String message;
        private final T data;

        public UserServiceResponse(boolean success, String message, T data) {
            this.message = message;
            this.success = success;
            this.data = data;
        }
    }

    public UserServiceResponse<String> loginUser(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            boolean passwordCheck = passwordEncoder.matches(password, user.getPassword());
            Role role = user.getRole();
            return new UserServiceResponse<>(passwordCheck, "Login Success", role.toString());
        }
        return new UserServiceResponse<>(false, "", null);
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserServiceResponse<User> getUserByUsername(String username) {
        try {
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                return new UserServiceResponse<>(true, "User fetched successfully", user);
            } else {
                return new UserServiceResponse<>(false, "User does not exist", null);
            }
        } catch (Exception e) {
            return new UserServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public UserServiceResponse<List<User>> getAllUsers() {
        try {
            List<User> allUsers = userRepository.findAll();
            if (!allUsers.isEmpty()) {
                return new UserServiceResponse<>(true, "Users fetched successfully", allUsers);
            } else {
                return new UserServiceResponse<>(false, "No users found", null);
            }
        } catch (Exception e) {
            return new UserServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public UserServiceResponse<Void> deleteUserByUsername(String username) {
        try {
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (!optionalUser.isPresent()) {
                return new UserServiceResponse<>(false, "User with this username not found", null);
            } else {
                User user = optionalUser.get();
                Long userID = user.getId();
                userRepository.deleteById(userID);
                return new UserServiceResponse<>(true, "User deleted successfully", null);

            }
        } catch (Exception e) {
            return new UserServiceResponse<>(false, e.getMessage(), null);
        }
    }

}