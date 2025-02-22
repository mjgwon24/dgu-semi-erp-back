package com.example.dgu_semi_erp_back.auth.dto;

import com.example.dgu_semi_erp_back.auth.entity.Role;
import lombok.Builder;

@Builder
public record SignUpRequest(
        String username,
        String password,
        String email,
        String nickname,
        Role role
) {}
