package com.example.dgu_semi_erp_back.dto.account;

import com.example.dgu_semi_erp_back.dto.common.PaginationInfo;
import com.example.dgu_semi_erp_back.entity.account.Account;
import com.example.dgu_semi_erp_back.dto.account.AccountHistoryCommandDto.AccountHistoryDetailResponse;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

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
            @NotNull
            String number,
            @NotNull
            long userId,
            @NotNull
            long clubId
    ) {}

    @Builder
    public record AccountInfoResponse(
            String number,
            Instant createdAt,
            Instant updatedAt,
            String ownerName,
            String clubName
    ) {}

    @Builder
    public record AccountInfoDetailResponse(
            String number,
            Instant createdAt,
            String ownerName,
            String clubName,
            List<AccountHistoryDetailResponse> accountHistories,
            PaginationInfo paginationInfo
    ) {}
}
