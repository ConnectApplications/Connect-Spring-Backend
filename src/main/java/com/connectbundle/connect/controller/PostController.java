package com.connectbundle.connect.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private AmazonS3 amazonS3;

    @PostMapping("/posts")
    public ResponseEntity<Post> savePost(@Valid @RequestBody Post post) {
        System.out.println(post);
        Post savedPost = postService.savePost(post);
        return ResponseEntity.ok(savedPost);
    }

    @PostMapping("/postsUpload")
    public ResponseEntity<String> imgUpload(@Valid @RequestBody MultipartFile file) throws IOException {
        System.out.println("filer" + file);
        String reply = postService.upload(file);
        return ResponseEntity.ok(reply);
    }

    @PostMapping("/postsUploads")
    public void imgUploaded() {
        amazonS3.listBuckets().forEach(bucket ->
                System.out.println("Bucket Name: " + bucket.getName())
        );
    }


}
