package com.example.dgu_semi_erp_back.dto.announcement;

import lombok.Builder;

import java.time.LocalDateTime;

public class AnnouncementCommandDto {
    private AnnouncementCommandDto() {}

    @Builder
    public record AnnouncementCreateRequest(
            String title,
            String content,
            String author,
            String file
    ) {}

    @Builder
    public record AnnouncementCreateResponse(
            Long id,
            String title,
            String content,
            String author,
            String file,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record AnnouncementUpdateRequest(
            Long id,
            String title,
            String content,
            String author,
            String file,
            LocalDateTime createAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record AnnouncementUpdateResponse(
            Long id,
            String title,
            String content,
            String author,
            String file,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}
