package com.example.dgu_semi_erp_back.dto.notification;

import com.example.dgu_semi_erp_back.entity.notification.Category;
import lombok.Builder;

import java.util.Map;

public final class NotificationCountDto {
    private NotificationCountDto() {}

    @Builder
    public record NotificationCategoryCountResponse(
            Map<Category, Long> categoryCounts
    ) {}
}
