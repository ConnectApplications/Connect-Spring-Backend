package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.PostsDTO.CreatePostDTO;
import com.connectbundle.connect.dto.PostsDTO.PostAuthorDTO;
import com.connectbundle.connect.dto.PostsDTO.PostResponseDTO;
import com.connectbundle.connect.dto.ProjectsDTO.ProjectResponseDTO;
import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.filter.IdGenerator;
import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.model.enums.PostTypeEnum;
import com.connectbundle.connect.model.enums.PostVisibilityEnum;
import com.connectbundle.connect.repository.PostsRepository;
import com.connectbundle.connect.repository.UserRepository;
import jakarta.persistence.PrePersist;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class PostsService {
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IdGenerator idGenerator;

    public ResponseEntity<BaseResponse<List<PostResponseDTO>>> getAllPosts(){
        List<Post> posts = postsRepository.findAll();
        List<PostResponseDTO> postResponseDTOS = posts.stream()
                .map(post -> {
                    PostResponseDTO postDTO = modelMapper.map(post, PostResponseDTO.class);

                    PostAuthorDTO authorDTO = modelMapper.map(post.getAuthor(), PostAuthorDTO.class);

                    postDTO.setAuthor(authorDTO);

                    return postDTO;
                })
                .toList();

        return BaseResponse.success(postResponseDTOS, "Posts fetched successfully", postResponseDTOS.size());

    }
    public ResponseEntity<BaseResponse<List<PostResponseDTO>>> getPostByUserId(Long id){
        List<Post> posts = postsRepository.findByAuthorId(id);
        List<PostResponseDTO> postResponseDTOS = posts.stream()
                .map(post -> {
                    PostResponseDTO postDTO = modelMapper.map(post, PostResponseDTO.class);

                    PostAuthorDTO authorDTO = modelMapper.map(post.getAuthor(), PostAuthorDTO.class);

                    postDTO.setAuthor(authorDTO);

                    return postDTO;
                })
                .toList();
        return BaseResponse.success(postResponseDTOS,"Posts fetched successfully",postResponseDTOS.size());
    }
    public ResponseEntity<BaseResponse<PostResponseDTO>> createPost(CreatePostDTO postDTO) throws NoSuchAlgorithmException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);
        if (authentication == null || !authentication.isAuthenticated()) {
            return BaseResponse.error("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }
        String username = authentication.getName(); // This is usually the email or username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));

        Post post = modelMapper.map(postDTO, Post.class);
        post.setId(idGenerator.generateUniqueId(user.getEmail()));
        post.setAuthor(user);

        Post savedPost = postsRepository.save(post);
        PostResponseDTO postResponseDTO = modelMapper.map(savedPost, PostResponseDTO.class);
        PostAuthorDTO authorDTO = modelMapper.map(postResponseDTO.getAuthor(), PostAuthorDTO.class);
        postResponseDTO.setAuthor(authorDTO);
        return BaseResponse.success(postResponseDTO, "Post saved successfully",1);
    }

//    public void deletePost(Long id){
//         postsRepository.deleteById(id);
//    }




}
