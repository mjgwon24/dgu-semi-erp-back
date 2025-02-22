package com.example.dgu_semi_erp_back.common.exception.response;

import lombok.Builder;

@Builder
public record ApiErrorResponse(
        int status,
        String code,
        String message
) {}