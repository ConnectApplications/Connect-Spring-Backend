package com.connectbundle.connect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectbundle.connect.model.OTP;

@Repository
public interface OtpRepository extends JpaRepository<OTP, Long> {
    public Optional<OTP> findByEmail(String email);

    public int deleteByEmail(String email);
}
