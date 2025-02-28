package com.connectbundle.connect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectbundle.connect.dto.PostsDTO.PostDto;
import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.model.enums.PostType;
import com.connectbundle.connect.model.enums.PostVisibility;
import com.connectbundle.connect.repository.PostRepository;
import lombok.Getter;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public PostServiceResponse<Post> createPost(PostDto postDto) {
        try {
            UserService.UserServiceResponse<User> userResponse = userService.getUserByID(postDto.getUserId());
            if (!userResponse.isSuccess()) {
                return new PostServiceResponse<>(false, "User not found", null);
            }

            Post post = new Post();
            post.setContent(postDto.getContent());
            post.setUser(userResponse.getData());
            post.setImage(postDto.getImage());
            post.setVisibility(postDto.getVisibility());
            post.setCreatedAt(postDto.getCreatedAt());
            post.setLikes(postDto.getLikes());
            post.setComments(postDto.getComments());
            post.setReposts(postDto.getReposts());
            post.setType(postDto.getType());

            Post savedPost = postRepository.save(post);
            return new PostServiceResponse<>(true, "Post created successfully", savedPost);
        } catch (Exception e) {
            return new PostServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public PostServiceResponse<Post> updatePost(Long id, PostDto postDto) {
        try {
            Optional<Post> optionalPost = postRepository.findById(id);
            if (!optionalPost.isPresent()) {
                return new PostServiceResponse<>(false, "Post not found", null);
            }

            Post post = optionalPost.get();
            
            // Only update user if it's changing
            if (post.getUser().getId() != postDto.getUserId()) {
                UserService.UserServiceResponse<User> userResponse = userService.getUserByID(postDto.getUserId());
                if (!userResponse.isSuccess()) {
                    return new PostServiceResponse<>(false, "User not found", null);
                }
                post.setUser(userResponse.getData());
            }
            
            post.setContent(postDto.getContent());
            post.setImage(postDto.getImage());
            post.setVisibility(postDto.getVisibility());
            post.setCreatedAt(postDto.getCreatedAt());
            post.setLikes(postDto.getLikes());
            post.setComments(postDto.getComments());
            post.setReposts(postDto.getReposts());
            post.setType(postDto.getType());

            Post updatedPost = postRepository.save(post);
            return new PostServiceResponse<>(true, "Post updated successfully", updatedPost);
        } catch (Exception e) {
            return new PostServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public PostServiceResponse<Post> getPostById(Long id) {
        try {
            Optional<Post> optionalPost = postRepository.findById(id);
            if (!optionalPost.isPresent()) {
                return new PostServiceResponse<>(false, "Post not found", null);
            }
            return new PostServiceResponse<>(true, "Post retrieved successfully", optionalPost.get());
        } catch (Exception e) {
            return new PostServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public PostServiceResponse<List<Post>> getAllPosts() {
        try {
            List<Post> posts = postRepository.findAll();
            return new PostServiceResponse<>(true, "Posts retrieved successfully", posts);
        } catch (Exception e) {
            return new PostServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public PostServiceResponse<List<Post>> getPostsByUser(Long userId) {
        try {
            UserService.UserServiceResponse<User> userResponse = userService.getUserByID(userId);
            if (!userResponse.isSuccess()) {
                return new PostServiceResponse<>(false, "User not found", null);
            }
            
            List<Post> posts = postRepository.findByUser(userResponse.getData());
            return new PostServiceResponse<>(true, "Posts retrieved successfully", posts);
        } catch (Exception e) {
            return new PostServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public PostServiceResponse<List<Post>> getPostsByType(PostType type) {
        try {
            List<Post> posts = postRepository.findByType(type);
            return new PostServiceResponse<>(true, "Posts retrieved successfully", posts);
        } catch (Exception e) {
            return new PostServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public PostServiceResponse<List<Post>> getPostsByVisibility(PostVisibility visibility) {
        try {
            List<Post> posts = postRepository.findByVisibility(visibility);
            return new PostServiceResponse<>(true, "Posts retrieved successfully", posts);
        } catch (Exception e) {
            return new PostServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public PostServiceResponse<List<Post>> getRecentPosts() {
        try {
            List<Post> posts = postRepository.findTop10ByOrderByCreatedAtDesc();
            return new PostServiceResponse<>(true, "Recent posts retrieved successfully", posts);
        } catch (Exception e) {
            return new PostServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public PostServiceResponse<Void> deletePost(Long id) {
        try {
            if (!postRepository.existsById(id)) {
                return new PostServiceResponse<>(false, "Post not found", null);
            }
            
            postRepository.deleteById(id);
            return new PostServiceResponse<>(true, "Post deleted successfully", null);
        } catch (Exception e) {
            return new PostServiceResponse<>(false, e.getMessage(), null);
        }
    }

    // Response class
    @Getter
    public static class PostServiceResponse<T> {
        private final boolean success;
        private final String message;
        private final T data;

        public PostServiceResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
    }
}
