package com.example.dgu_semi_erp_back.service.notification;

import com.example.dgu_semi_erp_back.dto.notification.NotificationQueryDto.NotificationResponse;
import com.example.dgu_semi_erp_back.entity.notification.Category;
import com.example.dgu_semi_erp_back.mapper.NotificationMapper;
import com.example.dgu_semi_erp_back.repository.notification.NotificationQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationQueryRepository notificationQueryRepository;
    private final NotificationMapper mapper;

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
}