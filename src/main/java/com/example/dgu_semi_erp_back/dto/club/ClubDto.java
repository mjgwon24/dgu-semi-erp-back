package com.example.dgu_semi_erp_back.dto.club;

import com.example.dgu_semi_erp_back.projection.club.ClubProjection.ClubSummery;
import lombok.Builder;

import java.util.List;

public final class ClubDto {
    private ClubDto(){}
    @Builder
    public record ClubResponse(
            List<ClubSummery> clubs
    ) {}
}
