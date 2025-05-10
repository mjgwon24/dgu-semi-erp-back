package com.example.dgu_semi_erp_back.service.notification;

import com.example.dgu_semi_erp_back.dto.notification.NotificationQueryDto.NotificationResponse;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.entity.notification.Category;
import com.example.dgu_semi_erp_back.entity.notification.Notification;
import com.example.dgu_semi_erp_back.mapper.NotificationMapper;
import com.example.dgu_semi_erp_back.repository.notification.NotificationCommandRepository;
import com.example.dgu_semi_erp_back.repository.notification.NotificationQueryRepository;
import com.example.dgu_semi_erp_back.dto.notification.NotificationCountDto.NotificationCategoryCountResponse;
import com.example.dgu_semi_erp_back.service.notification.SseEmitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationQueryRepository notificationQueryRepository;
    private final NotificationMapper mapper;
    private final NotificationCommandRepository notificationCommandRepository;
    private final SseEmitterService sseEmitterService;

    public Page<NotificationResponse> getNotifications(String categoryName, Long userId, Pageable pageable) {
        Category category = null;
        if (categoryName != null) {
            try {
                category = Category.valueOf(categoryName.toUpperCase());
            } catch (IllegalArgumentException ignored) {}
        }

        Page<NotificationResponse> page =
                notificationQueryRepository.findByCategoryAndUser(category, userId, pageable);

        return page;
    }

    public NotificationCategoryCountResponse getCategoryCounts(Long userId) {
        Map<Category, Long> counts = notificationQueryRepository.countByCategoryForUser(userId);
        return NotificationCategoryCountResponse.builder()
                .categoryCounts(counts)
                .build();
    }

    public void send(User user, Category category, String title, String content) {
        Notification notification = Notification.builder()
                .user(user)
                .category(category)
                .title(title)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        Notification savedNotification = notificationCommandRepository.save(notification);
        sseEmitterService.sendNotification(user.getId(), savedNotification);
    }
}