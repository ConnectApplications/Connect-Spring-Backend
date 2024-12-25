package com.connectbundle.connect.service;

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
    public static class UserServiceResponse {
        private final boolean success;
        private final String message;

        public UserServiceResponse(boolean success, String message) {
            this.message = message;
            this.success = success;
        }
    }

    public UserServiceResponse loginUser(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            boolean passwordCheck = passwordEncoder.matches(password, user.getPassword());
            Role role = user.getRole();
            return new UserServiceResponse(passwordCheck, role.toString());
        }
        return new UserServiceResponse(false, "");
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}