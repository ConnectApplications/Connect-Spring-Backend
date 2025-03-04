package com.connectbundle.connect.repository;

import com.connectbundle.connect.model.Comment;
import com.connectbundle.connect.model.Like;
import com.connectbundle.connect.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Post, String> {
}



