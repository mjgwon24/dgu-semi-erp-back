package com.example.dgu_semi_erp_back.dto;

import lombok.Builder;

@Builder
public record VerifyOtpRequest(
        String email,
        String otp,
        String otpRequestToken
) {}