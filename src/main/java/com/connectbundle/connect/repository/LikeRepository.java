package com.connectbundle.connect.repository;

import com.connectbundle.connect.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostIdAndUserId(String postId, Long userId);
    int countByPostId(String postId);
}
