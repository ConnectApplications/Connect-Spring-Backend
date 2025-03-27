package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.AuthDTO.LoginRequest;
import com.connectbundle.connect.dto.AuthDTO.LoginResponseDTO;
import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.PostsDTO.PostAuthorDTO;
import com.connectbundle.connect.dto.PostsDTO.PostResponseDTO;
import com.connectbundle.connect.dto.UserDTO.AddUserSkillDTO;
import com.connectbundle.connect.dto.UserDTO.CreateUserDTO;
import com.connectbundle.connect.dto.UserDTO.UpdateUserDTO;
import com.connectbundle.connect.dto.UserDTO.UserResponseDTO;
import com.connectbundle.connect.exception.ResourceAlreadyExistsException;
import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.model.User.User;
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
        newUser.setAchievement(userDTO.getAchievement());
        newUser.setInterest(userDTO.getInterest());
        newUser.setSocialLinks(userDTO.getSocialLinks());
        newUser.setStudentDetails(userDTO.getStudentDetails());
        newUser.setFacultyDetails(userDTO.getFacultyDetails());
        User savedUser = userRepository.save(newUser);
        UserResponseDTO userResponseDTO = modelMapper.map(savedUser, UserResponseDTO.class);
        userResponseDTO.setAchievement(savedUser.getAchievement());
        userResponseDTO.setInterest(savedUser.getInterest());
        userResponseDTO.setSocialLinks(savedUser.getSocialLinks());
        userResponseDTO.setStudentDetails(savedUser.getStudentDetails());
        userResponseDTO.setFacultyDetails(savedUser.getFacultyDetails());
        return BaseResponse.success(userResponseDTO, "User registered successfully", 1);
    }


    public ResponseEntity<BaseResponse<UserResponseDTO>> updateUser(String userName, UpdateUserDTO updateDTO) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userName", userName));

        if (updateDTO.getName() != null) user.setName(updateDTO.getName());
        if (updateDTO.getHeadline() != null) user.setHeadline(updateDTO.getHeadline());
        if (updateDTO.getAbout() != null) user.setAbout(updateDTO.getAbout());
        if (updateDTO.getLocation() != null) user.setLocation(updateDTO.getLocation());
        if (updateDTO.getIndustry() != null) user.setIndustry(updateDTO.getIndustry());
        if (updateDTO.getCurrentPosition() != null) user.setCurrentPosition(updateDTO.getCurrentPosition());

        if (updateDTO.getAchievement() != null) user.setAchievement(updateDTO.getAchievement());
        if (updateDTO.getInterest() != null) user.setInterest(updateDTO.getInterest());
        if (updateDTO.getSocialLinks() != null) user.setSocialLinks(updateDTO.getSocialLinks());

        if (updateDTO.getStudentDetails() != null) user.setStudentDetails(updateDTO.getStudentDetails());
        if (updateDTO.getFacultyDetails() != null) user.setFacultyDetails(updateDTO.getFacultyDetails());

        User updatedUser = userRepository.save(user);
        UserResponseDTO responseDTO = modelMapper.map(updatedUser, UserResponseDTO.class);
        responseDTO.setAchievement(updatedUser.getAchievement());
        responseDTO.setInterest(updatedUser.getInterest());
        responseDTO.setSocialLinks(updatedUser.getSocialLinks());
        responseDTO.setStudentDetails(updatedUser.getStudentDetails());
        responseDTO.setFacultyDetails(updatedUser.getFacultyDetails());

        return BaseResponse.success(responseDTO, "User updated successfully", 1);
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

    public ResponseEntity<BaseResponse<UserResponseDTO>> getUserByUsername(String username) {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));
            UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
            List<Post> posts = user.getPosts();
            List<PostResponseDTO> postResponseDTOS = posts.stream()
                    .map(post -> {
                        PostResponseDTO postDTO = modelMapper.map(post, PostResponseDTO.class);

                        PostAuthorDTO authorDTO = modelMapper.map(post.getAuthor(), PostAuthorDTO.class);

                        postDTO.setAuthor(authorDTO);

                        return postDTO;
                    })
                    .toList();
            userResponseDTO.setPost(postResponseDTOS);
        return BaseResponse.success(userResponseDTO,"User fetched successfully",1);
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

    public ResponseEntity<BaseResponse<List<UserResponseDTO>>> searchByNameAndRole(Role role, String name) {
        List<User> users;
        if (role != null) {
            if (name != null && !name.isEmpty()) {
                users = userRepository.findByRoleAndNameContainingIgnoreCase(role, name);
            } else {
                users = userRepository.findByRole(role);
            }

        } else {
            if (name != null && !name.isEmpty()) {
                users = userRepository.findByNameContainingIgnoreCase(name);
            } else {
                users = userRepository.findAll();
            }
        }

        List<UserResponseDTO> userResponseDTOS = users.stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .toList();
        return BaseResponse.success(userResponseDTOS,"Search users fetched",userResponseDTOS.size());

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