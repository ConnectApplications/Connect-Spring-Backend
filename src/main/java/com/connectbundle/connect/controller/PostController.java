package com.connectbundle.connect.controller;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.PostsDTO.CreatePostDTO;
import com.connectbundle.connect.dto.PostsDTO.PostResponseDTO;
import com.connectbundle.connect.dto.PostsDTO.UpdatePostDTO;
import com.connectbundle.connect.model.enums.PostTypeEnum;
import com.connectbundle.connect.service.PostsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    // Endpoint for multipart/form-data submissions (with file uploads)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<PostResponseDTO>> createPost(
            @RequestParam("content") String content,
            @RequestParam("type") PostTypeEnum type,
            @RequestParam(value = "userName",required = true) String userName,
            @RequestParam(value = "visibility", required = false) String visibility,
            @RequestParam(value = "tags", required = false) String[] tags,
            @RequestParam(value = "mediaFile", required = false) MultipartFile mediaFile) throws NoSuchAlgorithmException {

        CreatePostDTO postDTO = new CreatePostDTO();
        postDTO.setContent(content);
        postDTO.setType(type);
        postDTO.setVisibility(visibility);
        postDTO.setTags(tags);

        return postService.createPost(postDTO, mediaFile,userName);
    }





    // Endpoint for application/json submissions (no file uploads)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<PostResponseDTO>> createPostJson(
            @Valid @RequestBody CreatePostDTO postDTO) throws NoSuchAlgorithmException {
        return postService.createPost(postDTO, null,postDTO.getUserName());
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponseDTO>> updatePost(
            @Valid @RequestBody UpdatePostDTO postDTO,
            @PathVariable("id") String id
    ) {
        return postService.updatePost(postDTO,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deletePost(@PathVariable("id") String id) {
        return postService.deletePost(id);
    }
}