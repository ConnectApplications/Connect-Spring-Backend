package com.connectbundle.connect.repository;

import java.util.Optional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectbundle.connect.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//   Optional<User> findById(Long id);

   Optional<User> findByUsername(String username);

   Optional<User> findByEmail(String email);
}