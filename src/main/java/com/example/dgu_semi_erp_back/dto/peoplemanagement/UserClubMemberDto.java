package com.example.dgu_semi_erp_back.dto.peoplemanagement;

import com.example.dgu_semi_erp_back.entity.club.MemberStatus;
import com.example.dgu_semi_erp_back.entity.club.Role;
import lombok.Builder;

import java.time.LocalDateTime;

public final class UserClubMemberDto {
    private UserClubMemberDto() {}

    @Builder
    public record ClubMemberDetail(
            String name,
            String major,
            Integer studentNumber,
            Role role,
            LocalDateTime joinedAt,
            MemberStatus status
    ) {}
}