package com.example.dgu_semi_erp_back.dto.account;

import com.example.dgu_semi_erp_back.entity.account.Account;
import lombok.Builder;

import java.time.Instant;

public final class AccountCommandDto {
    private AccountCommandDto() {}

    @Builder
    public record AccountCreateRequest(
            String number,
            Instant createdAt,
            Long userId,
            Long clubId
    ){}

    @Builder
    public record AccountCreateResponse(
            Account account
    ) {}

    @Builder
    public record AccountUpdateRequest(
            String number,
            Instant updatedAt,
            long userId,
            long clubId
    ) {}

    @Builder
    public record AccountUpdateResponse(
            Account account
    ) {}
}
