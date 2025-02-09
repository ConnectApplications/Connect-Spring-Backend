package com.connectbundle.connect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.connectbundle.connect.model.Club;
import com.connectbundle.connect.model.ClubMember;
import com.connectbundle.connect.model.User;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    Optional<ClubMember> findByClubAndUser(Club club, User user);
}
