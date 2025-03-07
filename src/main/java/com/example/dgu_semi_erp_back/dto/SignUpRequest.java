package com.example.dgu_semi_erp_back.dto;

import lombok.Builder;

@Builder
public record SignUpRequest(
        String username,
        String password,
        String email,
        String nickname,
        String otpVerificationToken
) {}
