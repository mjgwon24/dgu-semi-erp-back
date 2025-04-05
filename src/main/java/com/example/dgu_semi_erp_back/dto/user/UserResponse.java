package com.example.dgu_semi_erp_back.dto.user;
import com.example.dgu_semi_erp_back.entity.club.Role;
import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String name,
        String email,
        Role role,
        Long clubId
) {}
