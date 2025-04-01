package com.connectbundle.connect.controller;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.PostsDTO.CommentRequest;
import com.connectbundle.connect.service.PostInteractionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts Interactions", description = "Posts Interactions")
public class PostInteractionController {

    @Autowired
    private  PostInteractionService postInteractionService;


    @PostMapping("/{postId}/like")
    public ResponseEntity<BaseResponse<Void>> likePost(@PathVariable String postId) {
        return postInteractionService.likePost(postId);
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<BaseResponse<Void>> commentOnPost(
            @PathVariable String postId,
            @RequestBody CommentRequest commentRequest) {
        return postInteractionService.commentOnPost(postId, commentRequest);
    }

    @PostMapping("/{postId}/bookmark")
    public ResponseEntity<BaseResponse<Void>> bookmarkPost(@PathVariable String postId) {
        return postInteractionService.bookmarkPost(postId);
    }
}
