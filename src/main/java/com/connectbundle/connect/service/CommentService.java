package com.connectbundle.connect.service;

import com.connectbundle.connect.model.Comment;
import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.repository.CommentRepository;
import com.connectbundle.connect.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostsRepository postRepository;

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment addComment(Long postId, User user, String content) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new RuntimeException("Post not found");
        }

        Comment comment = new Comment();
        comment.setPost(postOptional.get());
        comment.setUser(user);
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}