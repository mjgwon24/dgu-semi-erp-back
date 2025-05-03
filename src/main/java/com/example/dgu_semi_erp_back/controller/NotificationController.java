package com.example.dgu_semi_erp_back.controller;

import com.example.dgu_semi_erp_back.dto.notification.NotificationCountDto.NotificationCategoryCountResponse;
import com.example.dgu_semi_erp_back.dto.notification.NotificationQueryDto.NotificationResponse;
import com.example.dgu_semi_erp_back.service.notification.NotificationService;
import com.example.dgu_semi_erp_back.service.notification.SseEmitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final SseEmitterService sseEmitterService;

    @GetMapping
    public Page<NotificationResponse> getNotifications(@RequestParam(required = false) String category,
                                                       @RequestParam Long userId,
                                                       Pageable pageable) {
        return notificationService.getNotifications(category, userId, pageable);
    }

    @GetMapping("/subscribe")
    public SseEmitter subscribe(@RequestParam Long userId) {
        return sseEmitterService.subscribe(userId);
    }

    @GetMapping("/category-counts")
    public NotificationCategoryCountResponse getCategoryCounts(@RequestParam Long userId) {
        return notificationService.getCategoryCounts(userId);
    }

    /* sse 알림 테스트
@PostMapping("/send-test")
public void sendTestNotification(@RequestParam Long userId, @RequestBody Map<String, Object> payload) {
    sseEmitterService.sendNotification(userId, payload);
}
*/
}
