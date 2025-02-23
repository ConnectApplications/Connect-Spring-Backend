package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.UserDTO.AddUserSkillDTO;
import com.connectbundle.connect.dto.UserDTO.CreateUserDTO;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.model.UserSkill;
import com.connectbundle.connect.model.enums.Role;
import com.connectbundle.connect.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    @Autowired
    private S3Service s3Service;
    @Autowired
    private UserRepository userRepository;

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

    public User registerUser(CreateUserDTO user) {
        User newUser = new User();
        newUser.setProfilePicture("");
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

    public UserServiceResponse<Void> uploadUserImage(MultipartFile file, User user) {
        try {
            String uniqueFileName = s3Service.uploadFile(file, "UserProfilePicture");
            user.setProfilePicture("https://connect-beta-2" + ".s3.amazonaws.com/" + uniqueFileName);
            userRepository.save(user);
            return new UserServiceResponse<>(true, "Picture uploaded successfully", null);
        } catch (Exception e) {
            return new UserServiceResponse<>(false, e.getMessage(), null);
        }
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

    public UserServiceResponse<User> getUserByID(Long id) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                return new UserServiceResponse<>(true, "User fetched successfully", optionalUser.get());
            } else {
                return new UserServiceResponse<>(false, "User not found", null);
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

    public UserServiceResponse<Void> addSkillToUser(String username, AddUserSkillDTO skill) {
        try {
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (!optionalUser.isPresent()) {
                return new UserServiceResponse<>(false, "User with this username not found", null);
            } else {
                User user = optionalUser.get();
                UserSkill newSkill = new UserSkill();
                newSkill.setSkillName(skill.getSkillName());
                newSkill.setProficiencyLevel(skill.getProficiencyLevel());
                newSkill.setUser(user);
                user.getUserSkills().add(newSkill);
                userRepository.save(user);
                return new UserServiceResponse<>(true, "SKill added successfully", null);
            }
        } catch (Exception e) {
            return new UserServiceResponse<>(false, e.getMessage(), null);
        }
    }

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

}