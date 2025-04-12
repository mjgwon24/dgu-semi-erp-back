package com.example.dgu_semi_erp_back.dto.club;

import com.example.dgu_semi_erp_back.entity.club.MemberStatus;
import com.example.dgu_semi_erp_back.entity.club.Role;
import lombok.Builder;

import java.time.LocalDateTime;

import com.example.dgu_semi_erp_back.entity.club.MemberStatus;
import com.example.dgu_semi_erp_back.entity.club.Role;
import lombok.Builder;

import java.time.LocalDateTime;

public final class UserClubMemberDto {
    private UserClubMemberDto() {}

    @Builder
    public record ClubRegisterRequest(Long clubId,String role,String status) {}
    @Builder
    public record ClubRegisterResponse (String message){}
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