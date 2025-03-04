package com.connectbundle.connect.controller;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.PostsDTO.CreatePostDTO;
import com.connectbundle.connect.dto.PostsDTO.PostResponseDTO;
import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.repository.UserRepository;
import com.connectbundle.connect.service.PostsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Posts Endpoints")
public class PostController {

    @Autowired
    private PostsService postService;


    @GetMapping
    public ResponseEntity<BaseResponse<List<PostResponseDTO>>> getAllPosts() {
        return postService.getAllPosts();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
//        Optional<Post> post = postService.getPostByUserId(id);
//        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @PostMapping
    public ResponseEntity<BaseResponse<PostResponseDTO>> createPost(@Valid @RequestBody CreatePostDTO postDTO) throws NoSuchAlgorithmException {
            return postService.createPost(postDTO);
        }

//    @DeleteMapping("/{id}")
//    public void deletePost(@PathVariable Long id) {
//        postService.deletePost(id);
//    }


}