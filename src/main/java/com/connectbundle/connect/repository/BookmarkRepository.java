package com.connectbundle.connect.repository;

import com.connectbundle.connect.model.Post;
import com.connectbundle.connect.model.User.Bookmark;
import com.connectbundle.connect.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByPostAndUser(Post post, User user);
}
