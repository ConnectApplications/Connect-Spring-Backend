package com.connectbundle.connect.repository;

import com.connectbundle.connect.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<Post, String> {
    List<Post> findAllByOrderByCreatedAtDesc(); // Fetch all posts sorted by newest first
}