package com.connectbundle.connect.service;

import com.connectbundle.connect.model.Like;
import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.repository.LikeRepository;
import com.connectbundle.connect.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostsRepository postRepository;

    public void likePost(String postId, User user) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new RuntimeException("Post not found");
        }

        Optional<Like> existingLike = likeRepository.findByPostIdAndUserId(postId, user.getId());
        if (existingLike.isPresent()) {
            throw new RuntimeException("User already liked this post");
        }

        Like like = new Like();
        like.setPost(postOptional.get());
        like.setUser(user);
        likeRepository.save(like);
    }

    public void unlikePost(String postId, User user) {
        Optional<Like> likeOptional = likeRepository.findByPostIdAndUserId(postId, user.getId());
        likeOptional.ifPresent(likeRepository::delete);
    }

    public int getLikeCount(String postId) {
        return likeRepository.countByPostId(postId);
    }
}
