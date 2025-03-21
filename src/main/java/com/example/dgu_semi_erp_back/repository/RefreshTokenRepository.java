package com.example.dgu_semi_erp_back.repository;

import com.example.dgu_semi_erp_back.entity.User.RefreshToken;
import com.example.dgu_semi_erp_back.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(User user);
}
