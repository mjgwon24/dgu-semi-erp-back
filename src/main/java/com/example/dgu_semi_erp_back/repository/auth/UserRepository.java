package com.example.dgu_semi_erp_back.repository.auth;

import com.example.dgu_semi_erp_back.entity.auth.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);

}
