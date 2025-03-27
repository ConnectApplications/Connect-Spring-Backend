package com.connectbundle.connect.repository;

import java.util.List;
import java.util.Optional;


import com.connectbundle.connect.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectbundle.connect.model.User.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> findByUsernameOrEmail(String username, String email);
   Optional<User> findByUsername(String username);

   Optional<User> findByEmail(String email);

   List<User> findByRoleAndNameContainingIgnoreCase(Role role, String name);
   List<User> findByRole(Role role);

   List<User> findByNameContainingIgnoreCase(String name);


}