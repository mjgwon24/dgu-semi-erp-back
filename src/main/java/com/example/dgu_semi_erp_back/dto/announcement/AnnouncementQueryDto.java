package com.example.dgu_semi_erp_back.dto.announcement;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public final class AnnouncementQueryDto {
    private AnnouncementQueryDto() {}

    // 목록 조회
    @Builder
    public record AnnouncementSummariesListResponse(
            List<AnnouncementSummaryResponse> content,
            Integer pageNumber,
            Integer pageSize,
            Long totalElements,
            Integer totalPages,
            Boolean last
    ) {
        @Builder
        public record AnnouncementSummaryResponse(
                Long id,
                String title,
                String content,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) {}
    }
}