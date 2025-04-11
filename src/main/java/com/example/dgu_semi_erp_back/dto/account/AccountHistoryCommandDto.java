package com.example.dgu_semi_erp_back.dto.account;

import com.example.dgu_semi_erp_back.entity.account.PayType;
import lombok.Builder;

import java.time.Instant;

public final class AccountHistoryCommandDto {
    private AccountHistoryCommandDto() {}

    @Builder
    public record AccountHistoryCreateRequest(
            String content,
            int totalAmount,
            int usedAmount
    ) {}

    @Builder
    public record AccountHistoryCreateResponse(
            String content,
            int totalAmount,
            int usedAmount
    ) {}

    @Builder
    public record AccountHistoryDetailResponse(
            PayType payType,
            String content,
            int usedAmount,
            int totalAmount,
            Instant createdAt
    ) {}
}
