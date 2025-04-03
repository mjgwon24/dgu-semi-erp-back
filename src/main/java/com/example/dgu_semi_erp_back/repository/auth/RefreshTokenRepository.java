package com.example.dgu_semi_erp_back.repository.auth;

import com.example.dgu_semi_erp_back.entity.auth.RefreshToken;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(User user);
}
