package com.example.dgu_semi_erp_back.dto.notification;

import com.example.dgu_semi_erp_back.entity.notification.Category;
import lombok.Builder;

import java.time.LocalDateTime;

public final class NotificationQueryDto {
    private NotificationQueryDto() {}

    @Builder
    public record NotificationResponse(
            Long id,
            String title,
            String content,
            Category category,
            LocalDateTime date,
            Boolean read,
            boolean isNew // 3일 이내 생성된 경우 true
    ) {}
}