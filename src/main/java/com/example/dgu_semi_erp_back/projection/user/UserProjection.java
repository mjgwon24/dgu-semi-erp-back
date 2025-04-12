package com.example.dgu_semi_erp_back.projection.user;
import lombok.Builder;
public class UserProjection {
    @Builder
    public record UserDetail(
            Long id,
            String username,
            String email,
            String nickname,
            String major,
            Integer studentNumber,
            Boolean isVerified
    ){}
}