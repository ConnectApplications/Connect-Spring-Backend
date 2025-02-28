package com.connectbundle.connect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.PostsDTO.PostDto;
import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.model.enums.PostType;
import com.connectbundle.connect.model.enums.PostVisibility;
import com.connectbundle.connect.service.PostService;
import com.connectbundle.connect.service.PostService.PostServiceResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Post Management Endpoints")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    @Operation(summary = "Get All Posts", description = "Retrieve a list of all posts")
    public ResponseEntity<BaseResponse<List<Post>>> getAllPosts() {
        try {
            PostServiceResponse<List<Post>> response = postService.getAllPosts();
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(),
                        HttpStatus.OK, response.getData().size());
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recent")
    @Operation(summary = "Get Recent Posts", description = "Retrieve the 10 most recent posts")
    public ResponseEntity<BaseResponse<List<Post>>> getRecentPosts() {
        try {
            PostServiceResponse<List<Post>> response = postService.getRecentPosts();
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(),
                        HttpStatus.OK, response.getData().size());
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Post by ID", description = "Retrieve a post by its ID")
    public ResponseEntity<BaseResponse<Post>> getPostById(@PathVariable Long id) {
        try {
            PostServiceResponse<Post> response = postService.getPostById(id);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(), HttpStatus.OK, 1);
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get Posts by User", description = "Retrieve all posts created by a specific user")
    public ResponseEntity<BaseResponse<List<Post>>> getPostsByUser(@PathVariable Long userId) {
        try {
            PostServiceResponse<List<Post>> response = postService.getPostsByUser(userId);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(),
                        HttpStatus.OK, response.getData().size());
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get Posts by Type", description = "Retrieve all posts of a specific type")
    public ResponseEntity<BaseResponse<List<Post>>> getPostsByType(@PathVariable PostType type) {
        try {
            PostServiceResponse<List<Post>> response = postService.getPostsByType(type);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(),
                        HttpStatus.OK, response.getData().size());
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/visibility/{visibility}")
    @Operation(summary = "Get Posts by Visibility", description = "Retrieve all posts with a specific visibility setting")
    public ResponseEntity<BaseResponse<List<Post>>> getPostsByVisibility(@PathVariable PostVisibility visibility) {
        try {
            PostServiceResponse<List<Post>> response = postService.getPostsByVisibility(visibility);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(),
                        HttpStatus.OK, response.getData().size());
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @Operation(summary = "Create Post", description = "Create a new post")
    public ResponseEntity<BaseResponse<Post>> createPost(@Valid @RequestBody PostDto postDto) {
        try {
            PostServiceResponse<Post> response = postService.createPost(postDto);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(), HttpStatus.CREATED, 1);
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Post", description = "Update an existing post")
    public ResponseEntity<BaseResponse<Post>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostDto postDto) {
        try {
            PostServiceResponse<Post> response = postService.updatePost(id, postDto);
            if (response.isSuccess()) {
                return BaseResponse.success(response.getData(), response.getMessage(), HttpStatus.OK, 1);
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Post", description = "Delete a post by its ID")
    public ResponseEntity<BaseResponse<Void>> deletePost(@PathVariable Long id) {
        try {
            PostServiceResponse<Void> response = postService.deletePost(id);
            if (response.isSuccess()) {
                return BaseResponse.success(null, response.getMessage(), HttpStatus.OK, 0);
            } else {
                return BaseResponse.error(response.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
