package com.example.dgu_semi_erp_back.repository.notification;

import com.example.dgu_semi_erp_back.entity.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationCommandRepository extends JpaRepository<Notification, Long> {
}