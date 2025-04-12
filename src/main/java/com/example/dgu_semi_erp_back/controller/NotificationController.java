package com.example.dgu_semi_erp_back.controller;

import com.example.dgu_semi_erp_back.dto.notification.NotificationQueryDto.NotificationResponse;
import com.example.dgu_semi_erp_back.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public Page<NotificationResponse> getNotifications(@RequestParam(required = false) String category,
                                                       @RequestParam Long userId,
                                                       Pageable pageable) {
        return notificationService.getNotifications(category, userId, pageable);
    }
}
