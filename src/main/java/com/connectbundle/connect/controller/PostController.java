package com.connectbundle.connect.controller;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.PostsDTO.CreatePostDTO;
import com.connectbundle.connect.dto.PostsDTO.PostResponseDTO;
import com.connectbundle.connect.service.PostsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

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

    @GetMapping("/user/{username}")
    public ResponseEntity<BaseResponse<List<PostResponseDTO>>>  getPostByUser(@PathVariable String username) {
        return postService.getPostByUser(username);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<PostResponseDTO>> createPost(@Valid @RequestBody CreatePostDTO postDTO) throws NoSuchAlgorithmException {
            return postService.createPost(postDTO);
        }

//    @DeleteMapping("/{id}")
//    public void deletePost(@PathVariable Long id) {
//        postService.deletePost(id);
//    }


}