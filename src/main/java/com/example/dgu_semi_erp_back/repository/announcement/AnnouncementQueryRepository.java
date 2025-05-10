package com.example.dgu_semi_erp_back.repository.announcement;

import com.example.dgu_semi_erp_back.entity.announcement.Announcement;
import com.example.dgu_semi_erp_back.projection.announcement.AnnouncementProjection.AnnouncementSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AnnouncementQueryRepository extends JpaRepository<Announcement, Long> {
    Optional<Announcement> findAnnouncementById(Long id);
    Page<AnnouncementSummary> findAnnouncementSummariesBy(Pageable pageable);
}
