package com.example.dgu_semi_erp_back.dto.user;
import com.example.dgu_semi_erp_back.entity.club.MemberStatus;
import com.example.dgu_semi_erp_back.entity.club.Role;
import lombok.Builder;

import java.time.LocalDateTime;


public final class UserCommandDto{
    @Builder
    public record UserResponse(
            Long id,
            String name,
            String email,
            Role role,
            Long clubId
    ) {}
    @Builder
    public record UserUpdateRequest(
            String username,
            String password,
            String email,
            String nickname,
            String major,
            Integer studentNumber,
            Role role
    ){}
    @Builder
    public record UserRoleUpdateRequest(
            Role role
    ){}
    @Builder
    public record UserEmailUpdateRequest(
            String email
    ){}
    @Builder
    public record UserUpdateResponse(
            String message
    ){}
}

