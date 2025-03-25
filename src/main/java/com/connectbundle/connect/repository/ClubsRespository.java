package com.connectbundle.connect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.connectbundle.connect.model.Club;

import java.util.Optional;

public interface ClubsRespository extends JpaRepository<Club, Long> {
    Optional<Club> findByName(String name);
}
