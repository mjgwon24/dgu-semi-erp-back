package com.example.dgu_semi_erp_back.dto.auth;

import com.example.dgu_semi_erp_back.entity.club.Role;
import lombok.Builder;

@Builder
public record SignUpRequest(
        String username,
        String password,
        String email,
        String nickname,
        String otpVerificationToken,
        String major,
        Integer studentNumber,
        Long clubId,
        Role role
) {}
