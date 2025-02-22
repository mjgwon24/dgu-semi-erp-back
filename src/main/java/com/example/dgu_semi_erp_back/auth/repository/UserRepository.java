package com.example.dgu_semi_erp_back.auth.repository;

import com.example.dgu_semi_erp_back.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
