package com.connectbundle.connect.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.connectbundle.connect.model.User;
import com.connectbundle.connect.repository.UserRepository;


@Service
public class UserService {

   @Autowired
   private UserRepository userRepository;
   private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public boolean loginUser (String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    public User registerUser (User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}