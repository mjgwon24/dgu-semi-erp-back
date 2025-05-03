package com.example.dgu_semi_erp_back.dto.user;
import com.example.dgu_semi_erp_back.entity.club.Role;
import com.example.dgu_semi_erp_back.projection.club.ClubProjection;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;


public final class UserCommandDto{
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
            @NotNull Long clubId,
            @NotNull Role role
    ){}
    @Builder
    public record UserEmailUpdateRequest(
            @NotNull String email
    ){}

    @Builder
    public record UserResponse(
            Long id,
            String name,
            String email
    ) {}
    @Builder
    public record UserRoleUpdateResponse(
            String message,
            Role role
    ){}
    @Builder
    public record UserEmailUpdateResponse(
            String message,
            String email
    ){}
}

