package com.example.dgu_semi_erp_back.dto.auth;

import lombok.Builder;

@Builder
public record SignInRequest(
        String username,
        String password
) {}
