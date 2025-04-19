package com.example.dgu_semi_erp_back.dto.account;

import com.example.dgu_semi_erp_back.dto.common.PaginationInfo;
import com.example.dgu_semi_erp_back.entity.account.Account;
import com.example.dgu_semi_erp_back.dto.account.AccountHistoryCommandDto.AccountHistoryDetailResponse;
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
            String number,
            Instant updatedAt,
            long userId,
            long clubId
    ) {}

    @Builder
    public record AccountUpdateResponse(
            Account account
    ) {}

    @Builder
    public record AccountInfoResponse(
            String number,
            Instant createdAt,
            String ownerName,
            String clubName,
            List<AccountHistoryDetailResponse> accountHistories,
            PaginationInfo paginationInfo
    ) {}
}
