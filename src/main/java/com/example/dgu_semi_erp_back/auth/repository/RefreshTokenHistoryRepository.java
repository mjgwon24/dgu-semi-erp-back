package com.example.dgu_semi_erp_back.auth.repository;

import com.example.dgu_semi_erp_back.auth.entity.RefreshTokenHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenHistoryRepository extends JpaRepository<RefreshTokenHistory, Long> {
    Optional<RefreshTokenHistory> findByToken(String Token);
}
