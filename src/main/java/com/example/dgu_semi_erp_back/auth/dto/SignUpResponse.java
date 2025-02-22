package com.example.dgu_semi_erp_back.auth.dto;

import lombok.Builder;

@Builder
public record SignUpResponse (
        String message,
        String userName,
        String nickName
) {
}
