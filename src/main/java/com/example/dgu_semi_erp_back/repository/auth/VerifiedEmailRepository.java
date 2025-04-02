package com.example.dgu_semi_erp_back.repository.auth;

import com.example.dgu_semi_erp_back.entity.auth.VerifiedEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerifiedEmailRepository extends JpaRepository<VerifiedEmail, Long> {
    Optional<VerifiedEmail> findByEmail(String email);
}