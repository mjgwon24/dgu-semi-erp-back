package com.example.dgu_semi_erp_back.dto.account;

import com.example.dgu_semi_erp_back.dto.common.PaginationInfo;
import com.example.dgu_semi_erp_back.dto.account.AccountHistoryCommandDto.AccountHistoryDetailResponse;
import com.example.dgu_semi_erp_back.entity.auth.user.UserRole;
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
            Long accountId,
            String accountNumber,
            Instant createdAt,
            Instant updatedAt,
            UserInfo user,
            ClubInfo club
    ) {
        @Builder
        public record UserInfo(
                Long userId,
                String userName,
                String userEmail,
                String nickname,
                String major,
                int studentNumber,
                UserRole role
        ) {}

        @Builder
        public record ClubInfo(
                Long clubId,
                String clubName,
                String affiliation
        ) {}
    }

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

    @Builder
    public record AccountClubListResponse(
            List<AccountCreateResponse.ClubInfo> clubs,
            PaginationInfo paginationInfo
    ) {}
}
