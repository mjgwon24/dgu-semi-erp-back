package com.example.dgu_semi_erp_back.projection.announcement;

import lombok.Builder;

import java.time.LocalDateTime;

public final class AnnouncementProjection {
    @Builder
    public record AnnouncementSummary(
            Long id,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}
