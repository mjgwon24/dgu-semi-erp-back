package com.example.dgu_semi_erp_back.repository.announcement;

import com.example.dgu_semi_erp_back.entity.announcement.Announcement;
import com.example.dgu_semi_erp_back.projection.announcement.AnnouncementProjection.AnnouncementSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

// Get 전용
public interface AnnouncementQueryRepository extends JpaRepository<Announcement, Long> {
    // Detail 조회
    Optional<Announcement> findAnnouncementById(Long id);
    // 목록 조회
    Page<AnnouncementSummary> findAnnouncementSummariesBy(Pageable pageable);
}
