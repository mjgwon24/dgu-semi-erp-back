package com.example.dgu_semi_erp_back.repository.notification;

import com.example.dgu_semi_erp_back.dto.notification.NotificationQueryDto.NotificationResponse;
import com.example.dgu_semi_erp_back.entity.notification.QNotification;
import com.example.dgu_semi_erp_back.entity.notification.Category;
import com.example.dgu_semi_erp_back.mapper.NotificationMapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class NotificationQueryRepositoryImpl implements NotificationQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final NotificationMapper mapper;

    @Override
    public Page<NotificationResponse> findByCategoryAndUser(Category category, Long userId, Pageable pageable) {
        QNotification notification = QNotification.notification;

        BooleanBuilder builder = new BooleanBuilder();
        if (category != null) builder.and(notification.category.eq(category));
        if (userId != null) builder.and(notification.user.id.eq(userId));

        List<NotificationResponse> content = queryFactory
                .selectFrom(notification)
                .where(builder)
                .orderBy(notification.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(mapper::toDto)
                .toList();

        Long count = queryFactory
                .select(notification.count())
                .from(notification)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, count != null ? count : 0);
    }

    @Override
    public Map<Category, Long> countByCategoryForUser(Long userId) {
        QNotification notification = QNotification.notification;

        List<Tuple> results = queryFactory
            .select(notification.category, notification.count())
            .from(notification)
            .where(notification.user.id.eq(userId))
            .groupBy(notification.category)
            .fetch();

        return results.stream()
            .collect(Collectors.toMap(
                tuple -> tuple.get(notification.category),
                tuple -> tuple.get(notification.count())
            ));
    }

}