package com.example.dgu_semi_erp_back.mapper;

import com.example.dgu_semi_erp_back.dto.notification.NotificationQueryDto.NotificationResponse;
import com.example.dgu_semi_erp_back.entity.notification.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "date", source = "createdAt")
    @Mapping(target = "read", source = "isRead")
    @Mapping(target = "isNew", expression = "java(isNew(notification.getCreatedAt()))")
    NotificationResponse toDto(Notification notification);

    default boolean isNew(LocalDateTime createdAt) {
        return createdAt != null && createdAt.isAfter(LocalDateTime.now().minusDays(3));
    }
}