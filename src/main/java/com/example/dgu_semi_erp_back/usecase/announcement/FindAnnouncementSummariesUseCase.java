package com.example.dgu_semi_erp_back.usecase.announcement;

import com.example.dgu_semi_erp_back.projection.announcement.AnnouncementProjection.AnnouncementSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;

// 목록 조회시 쓰는 명세
public interface FindAnnouncementSummariesUseCase {
    Page<AnnouncementSummary> findAnnouncementSummaries(Pageable pageable,
                                                       LocalDateTime startDate,
                                                       LocalDateTime endDate);
}
