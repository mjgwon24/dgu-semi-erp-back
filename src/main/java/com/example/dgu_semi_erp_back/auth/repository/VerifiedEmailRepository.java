package com.example.dgu_semi_erp_back.auth.repository;

import com.example.dgu_semi_erp_back.auth.entity.VerifiedEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerifiedEmailRepository extends JpaRepository<VerifiedEmail, Long> {
    Optional<VerifiedEmail> findByEmail(String email);
}