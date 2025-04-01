package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.PostsDTO.CreatePostDTO;
import com.connectbundle.connect.dto.PostsDTO.PostAuthorDTO;
import com.connectbundle.connect.dto.PostsDTO.PostResponseDTO;
import com.connectbundle.connect.dto.PostsDTO.UpdatePostDTO;
import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.filter.IdGenerator;
import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.model.User.User;
import com.connectbundle.connect.repository.PostsRepository;
import com.connectbundle.connect.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PostsService {
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private S3Service s3Service;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IdGenerator idGenerator;

    @Value("${s3_public_url_prefix}")
    private String S3_PUBLIC_URL_PREFIX;


    public ResponseEntity<BaseResponse<List<PostResponseDTO>>> getAllPosts(){
        List<Post> posts = postsRepository.findAll();
        List<PostResponseDTO> postResponseDTOS = posts.stream()
                .map(this::mapPostToResponseDTO)
                .toList();

        return BaseResponse.success(postResponseDTOS, "Posts fetched successfully", postResponseDTOS.size());

    }
    public ResponseEntity<BaseResponse<List<PostResponseDTO>>> getPostByUser(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));
        List<Post> posts = postsRepository.findByAuthorId(user.getId());
        List<PostResponseDTO> postResponseDTOS = posts.stream()
                .map(this::mapPostToResponseDTO)
                .toList();

        return BaseResponse.success(postResponseDTOS,"Posts fetched successfully",postResponseDTOS.size());
    }
    public ResponseEntity<BaseResponse<PostResponseDTO>> createPost(CreatePostDTO postDTO, MultipartFile mediaFile,String userName) throws NoSuchAlgorithmException {
        // Validate required fields
        if (postDTO.getContent() == null || postDTO.getContent().trim().isEmpty()) {
            return BaseResponse.error("Post content is required", HttpStatus.BAD_REQUEST);
        }
        
        if (postDTO.getType() == null) {
            return BaseResponse.error("Post type is required", HttpStatus.BAD_REQUEST);
        }
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return BaseResponse.error("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }
//        String username = authentication.getName();
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username", userName));

        Post post = modelMapper.map(postDTO, Post.class);
        post.setId(idGenerator.generateUniqueId(user.getEmail()));
        post.setAuthor(user);
        
        // Handle file upload if media is provided
        if (mediaFile != null && !mediaFile.isEmpty()) {
            try {
                String key = s3Service.uploadFile(mediaFile, "post/");
                post.setMedia(key);
            } catch (Exception e) {
                // Log the error but don't fail the whole post creation
                System.err.println("Failed to upload media file: " + e.getMessage());
                // Set media to null to indicate no media was uploaded
                post.setMedia(null);
            }
        }

        Post savedPost = postsRepository.save(post);
        PostResponseDTO postResponseDTO = mapPostToResponseDTO(savedPost);


        return BaseResponse.success(postResponseDTO, "Post saved successfully", 1);
    }

    public ResponseEntity<BaseResponse<PostResponseDTO>> createPostGuest(CreatePostDTO postDTO, MultipartFile mediaFile,String username) throws NoSuchAlgorithmException {
        // Validate required fields
        if (postDTO.getContent() == null || postDTO.getContent().trim().isEmpty()) {
            return BaseResponse.error("Post content is required", HttpStatus.BAD_REQUEST);
        }

        if (postDTO.getType() == null) {
            return BaseResponse.error("Post type is required", HttpStatus.BAD_REQUEST);
        }



        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));

        Post post = modelMapper.map(postDTO, Post.class);
        post.setId(idGenerator.generateUniqueId(user.getEmail()));
        post.setAuthor(user);

        // Handle file upload if media is provided
        if (mediaFile != null && !mediaFile.isEmpty()) {
            try {
                String key = s3Service.uploadFile(mediaFile, "post/");
                post.setMedia(key);
            } catch (Exception e) {
                // Log the error but don't fail the whole post creation
                System.err.println("Failed to upload media file: " + e.getMessage());
                // Set media to null to indicate no media was uploaded
                post.setMedia(null);
            }
        }

        Post savedPost = postsRepository.save(post);
        PostResponseDTO postResponseDTO = mapPostToResponseDTO(savedPost);


        return BaseResponse.success(postResponseDTO, "Post saved successfully", 1);
    }


    public ResponseEntity<BaseResponse<PostResponseDTO>> updatePost(UpdatePostDTO postDTO,String id)
    {
        Post post = postsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return BaseResponse.error("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));

        if (!post.getAuthor().getId().equals(user.getId())) {
            return BaseResponse.error("You are not authorized to edit this post", HttpStatus.FORBIDDEN);
        }

        if (postDTO.getContent() != null) post.setContent(postDTO.getContent());
        if (postDTO.getType() != null) post.setType(postDTO.getType());
        if (postDTO.getVisibility() != null) post.setVisibility(postDTO.getVisibility());
        if (postDTO.getTags() != null) post.setTags(postDTO.getTags());
//        if (postDTO.getMedia() != null) post.setMedia(postDTO.getMedia());

        Post savedPost = postsRepository.save(post);
        PostResponseDTO postResponseDTO = mapPostToResponseDTO(savedPost);

        return BaseResponse.success(postResponseDTO, "Post saved successfully",1);
    }
    public ResponseEntity<BaseResponse<Void>> deletePost(String id) {
        Post post = postsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return BaseResponse.error("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));

        if (!post.getAuthor().getId().equals(user.getId())) {
            return BaseResponse.error("You are not authorized to delete this post", HttpStatus.FORBIDDEN);
        }

        postsRepository.delete(post);
        return BaseResponse.success(null, "Post deleted successfully", 1);
    }

    private PostResponseDTO mapPostToResponseDTO(Post post) {
        PostResponseDTO dto = modelMapper.map(post, PostResponseDTO.class);
        PostAuthorDTO authorDTO = modelMapper.map(post.getAuthor(), PostAuthorDTO.class);
        dto.setAuthor(authorDTO);

        if (post.getMedia() != null && !post.getMedia().isBlank()) {
            dto.setMedia(S3_PUBLIC_URL_PREFIX + post.getMedia());
        }

        return dto;
    }




}
