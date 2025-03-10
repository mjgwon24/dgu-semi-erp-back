package com.example.dgu_semi_erp_back.auth.repository;

import com.example.dgu_semi_erp_back.auth.entity.RefreshToken;
import com.example.dgu_semi_erp_back.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUser(User user);
    void deleteAllByUserId(Long userId);

}
