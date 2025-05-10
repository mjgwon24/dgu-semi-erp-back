package com.example.dgu_semi_erp_back.usecase.announcement;

import com.example.dgu_semi_erp_back.projection.announcement.AnnouncementProjection.AnnouncementSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;

public interface FindAnnouncementSummariesUseCase {
    Page<AnnouncementSummary> findAnnouncementSummaries(Pageable pageable,
                                                       String title,
                                                       String content,
                                                       LocalDateTime createdAt,
                                                       LocalDateTime updatedAt);
}
