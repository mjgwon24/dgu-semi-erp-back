package com.example.dgu_semi_erp_back.repository.notification;

import com.example.dgu_semi_erp_back.dto.notification.NotificationQueryDto.NotificationResponse;
import com.example.dgu_semi_erp_back.entity.notification.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface NotificationQueryRepository {
    Page<NotificationResponse> findByCategoryAndUser(Category category, Long userId, Pageable pageable);
    Map<Category, Long> countByCategoryForUser(Long userId);
}