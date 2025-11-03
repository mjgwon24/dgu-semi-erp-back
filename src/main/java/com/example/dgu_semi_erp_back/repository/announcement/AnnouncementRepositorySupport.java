package com.example.dgu_semi_erp_back.repository.announcement;

import com.example.dgu_semi_erp_back.entity.announcement.Announcement;
import com.example.dgu_semi_erp_back.entity.announcement.QAnnouncement;
import com.example.dgu_semi_erp_back.projection.announcement.AnnouncementProjection;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.dsl.PathBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Repository
public class AnnouncementRepositorySupport extends QuerydslRepositorySupport {
    QAnnouncement announcement = QAnnouncement.announcement;

    public AnnouncementRepositorySupport() {
        super(Announcement.class);
    }

    // 페이지에 따라 받아올 필터
    public Page<AnnouncementProjection.AnnouncementSummary> findFilteredAnnouncements(
            Pageable pageable,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        // 조건
        BooleanExpression conditions = createFilterConditions(
                startDate, endDate
        );
        // 쿼리 생성
        var query = getQuerydsl().createQuery()
                // 어떤 필드에서 가져올건지
                .select(Projections.constructor(
                        AnnouncementProjection.AnnouncementSummary.class,
                        announcement.id,
                        announcement.title,
                        announcement.content,
                        announcement.createdAt,
                        announcement.updatedAt
                ))
                .from(announcement)
                .where(conditions) // 조건에 따라
                .offset(pageable.getOffset()) // 페이지네이션 시작
                .limit(pageable.getPageSize()); // 페이지네이션 따라서

        // 기간에 따라 Sorting 적용
        for (Sort.Order order : pageable.getSort()) {
            PathBuilder<?> pathBuilder = new PathBuilder<>(Announcement.class, "announcement");
            query.orderBy(
                    order.isAscending()
                            ? pathBuilder.getComparable(order.getProperty(), Comparable.class).asc()
                            : pathBuilder.getComparable(order.getProperty(), Comparable.class).desc()
            );
        }

        var result = query.fetch();

        // Total count 쿼리
        Long total = getQuerydsl().createQuery()
                .select(announcement.count())
                .from(announcement)
                .where(conditions)
                .fetchOne();

        return new PageImpl<>(result, pageable, total != null ? total : 0L);
    }

    // 조건 생성
    private BooleanExpression createFilterConditions(
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        BooleanExpression conditions = announcement.isNotNull();
        conditions = conditions.and(announcement.updatedAt.isNotNull());


        if (startDate != null && endDate != null) {
            conditions = conditions.and(
                    announcement.updatedAt.between(
                            startDate.toLocalDate().atStartOfDay(),
                            endDate.toLocalDate().atTime(LocalTime.MAX)
                    )
            );
        } else if (startDate != null) {
            conditions = conditions.and(announcement.updatedAt.goe(startDate.toLocalDate().atStartOfDay()));
        } else if (endDate != null) {
            conditions = conditions.and(announcement.updatedAt.loe(endDate.toLocalDate().atTime(LocalTime.MAX)));
        }

        return conditions;
    }
}
