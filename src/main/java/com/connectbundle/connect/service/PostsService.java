package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.PostsDTO.CreatePostDTO;
import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.model.enums.PostTypeEnum;
import com.connectbundle.connect.model.enums.PostVisibilityEnum;
import com.connectbundle.connect.repository.PostsRepository;
import com.connectbundle.connect.repository.UserRepository;
import jakarta.persistence.PrePersist;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostsService {
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Post> getAllPosts(){
        return postsRepository.findAll();
    }
    public Optional<Post> getPostByUserId(Long id){
        return postsRepository.findById(id);
    }
    public Post createPost(CreatePostDTO postDTO) {
        User user = userRepository.findById(postDTO.getAuthor_id())
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", postDTO.getAuthor_id()));

        // Use ModelMapper to map DTO to Post entity
        Post post = modelMapper.map(postDTO, Post.class);
        post.setUser(user);  // Set User object after mapping

        return postsRepository.save(post);
    }

    public void deletePost(Long id){
         postsRepository.deleteById(id);
    }




}
