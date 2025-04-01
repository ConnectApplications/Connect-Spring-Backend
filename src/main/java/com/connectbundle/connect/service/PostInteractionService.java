package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.PostsDTO.CommentRequest;
import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.model.Comment;
import com.connectbundle.connect.model.Like;
import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.model.User.Bookmark;
import com.connectbundle.connect.model.User.User;
import com.connectbundle.connect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostInteractionService {
    @Autowired
    private PostsRepository postRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private  CommentRepository commentRepository;
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<BaseResponse<Void>> likePost(String postId) {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        boolean alreadyLiked = post.getLikes().stream()
                .anyMatch(like -> like.getUser().getId().equals(user.getId()));
        if (!alreadyLiked) {
            Like like = new Like();
            like.setPost(post);
            like.setUser(user);
            likeRepository.save(like);
            post.getLikes().add(like);
            postRepository.save(post);
        }
        return BaseResponse.successMessage("Post liked successfully");
    }

    public ResponseEntity<BaseResponse<Void>> commentOnPost(String postId, CommentRequest commentRequest) {
        // Get current user from security context
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
        post.getComments().add(comment);
        postRepository.save(post);

        return BaseResponse.successMessage( "Comment added successfully");
    }

    public ResponseEntity<BaseResponse<Void>> bookmarkPost(String postId) {
        // Get current user from security context
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Check if the post is already bookmarked by this user
        Optional<Bookmark> existingBookmark = bookmarkRepository.findByPostAndUser(post, user);
        if (existingBookmark.isEmpty()) {
            Bookmark bookmark = new Bookmark();
            bookmark.setPost(post);
            bookmark.setUser(user);
            bookmarkRepository.save(bookmark);
        }
        return BaseResponse.successMessage("Post bookmarked successfully");
    }
}
