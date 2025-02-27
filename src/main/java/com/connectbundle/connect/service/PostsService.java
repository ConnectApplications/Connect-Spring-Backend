package com.connectbundle.connect.service;

import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostsService {
    @Autowired
    private PostsRepository postsRepository;

    public List<Post> getAllPosts(){
        return postsRepository.findAll();
    }
    public Optional<Post> getPostByUserId(Long id){
        return postsRepository.findById(id);
    }
    public Post createPost(Post post){
        return postsRepository.save(post);
    }
    public void deletePost(Long id){
         postsRepository.deleteById(id);
    }


}
