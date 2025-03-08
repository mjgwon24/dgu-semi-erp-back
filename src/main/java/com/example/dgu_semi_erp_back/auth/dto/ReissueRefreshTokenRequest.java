package com.example.dgu_semi_erp_back.auth.dto;

public record ReissueRefreshTokenRequest(
    String token,
    String username
) {}
