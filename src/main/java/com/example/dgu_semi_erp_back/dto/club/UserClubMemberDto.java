package com.example.dgu_semi_erp_back.dto.club;

import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanQueryDto;
import com.example.dgu_semi_erp_back.dto.common.PaginationInfo;
import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.entity.club.*;
import com.example.dgu_semi_erp_back.projection.club.ClubMemberProjection;
import com.example.dgu_semi_erp_back.projection.club.ClubProjection;
import com.example.dgu_semi_erp_back.projection.club.ClubProjection.ClubSummary;
import lombok.Builder;

import java.time.LocalDateTime;

import com.example.dgu_semi_erp_back.entity.club.MemberStatus;
import com.example.dgu_semi_erp_back.entity.club.Role;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public final class UserClubMemberDto {
    private UserClubMemberDto() {}

    @Builder
    public record ClubRegisterRequest(Long clubId,String role,String status) {}
    @Builder
    public record ClubRegisterResponse (String message, Club club){}
    @Builder
    public record ClubLeaveRequest(Long userId,Long clubId){}
    @Builder
    public record ClubLeaveResponse(String message, ClubProjection.ClubDetail club){}

    @Builder
    public record ClubMemberDetail(
            String name,
            String major,
            Integer studentNumber,
            Role role,
            LocalDateTime joinedAt,
            MemberStatus status
    ) {}
    @Builder
    public record ClubMemberDetailSearchResponse(
            ClubProjection.ClubDetail club,
            List<ClubMemberDetail> content,
            PaginationInfo paginationInfo
    ){}
    @Builder
    public record ClubMemberSearchResponse(
            List<ClubSummary> content,
            PaginationInfo paginationInfo
    ){}
}