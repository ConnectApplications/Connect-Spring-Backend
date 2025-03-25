package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.AuthDTO.LoginRequest;
import com.connectbundle.connect.dto.AuthDTO.LoginResponseDTO;
import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.UserDTO.AddUserSkillDTO;
import com.connectbundle.connect.dto.UserDTO.CreateUserDTO;
import com.connectbundle.connect.dto.UserDTO.UserResponseDTO;
import com.connectbundle.connect.exception.ResourceAlreadyExistsException;

import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.model.UserSkill;
import com.connectbundle.connect.model.enums.Role;
import com.connectbundle.connect.repository.UserRepository;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    JWTService jwtService;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<BaseResponse<LoginResponseDTO>> loginUser(LoginRequest loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                        .orElseThrow(() -> new ResourceNotFoundException("User", "Username", loginRequestDTO.getUsername()));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            return BaseResponse.error("Incorrect password", HttpStatus.UNAUTHORIZED);
        }
        String token = jwtService.generateToken(user, user.getRole().toString());
        UserResponseDTO responseUser = modelMapper.map(user, UserResponseDTO.class);
        LoginResponseDTO responseDTO = new LoginResponseDTO(responseUser, token);
        return BaseResponse.success(responseDTO, "Login Success", 1);
    }

    public ResponseEntity<BaseResponse<UserResponseDTO>> registerUser(CreateUserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new ResourceAlreadyExistsException("User", "username", userDTO.getUsername());
        }
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("User", "email", userDTO.getEmail());
        }
        User newUser = modelMapper.map(userDTO, User.class);
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedUser = userRepository.save(newUser);
        UserResponseDTO userResponseDTO = modelMapper.map(savedUser, UserResponseDTO.class);
        return BaseResponse.success(userResponseDTO, "User registered successfully", 1);
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
            return optionalUser.map(user -> new UserServiceResponse<>(true, "User fetched successfully", user)).orElseGet(() -> new UserServiceResponse<>(false, "User not found", null));
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