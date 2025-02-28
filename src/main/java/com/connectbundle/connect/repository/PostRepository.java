package com.connectbundle.connect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.model.enums.PostType;
import com.connectbundle.connect.model.enums.PostVisibility;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    List<Post> findByUser(User user);
    
    List<Post> findByType(PostType type);
    
    List<Post> findByVisibility(PostVisibility visibility);
    
    List<Post> findByUserAndType(User user, PostType type);
    
    List<Post> findTop10ByOrderByCreatedAtDesc();
}
