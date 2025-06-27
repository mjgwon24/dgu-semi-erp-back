package com.example.dgu_semi_erp_back.service.announcement;

import com.example.dgu_semi_erp_back.common.exception.CustomException;
import com.example.dgu_semi_erp_back.common.exception.ErrorCode;
import com.example.dgu_semi_erp_back.entity.announcement.Announcement;
import com.example.dgu_semi_erp_back.mapper.AnnouncementDtoMapper;
import com.example.dgu_semi_erp_back.projection.announcement.AnnouncementProjection.AnnouncementSummary;
import com.example.dgu_semi_erp_back.repository.announcement.AnnouncementQueryRepository;
import com.example.dgu_semi_erp_back.repository.announcement.AnnouncementRepositorySupport;
import com.example.dgu_semi_erp_back.usecase.announcement.AnnouncementUseCase;
import com.example.dgu_semi_erp_back.usecase.announcement.FindAnnouncementSummariesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnnouncementQueryService implements AnnouncementUseCase, FindAnnouncementSummariesUseCase {
    private final AnnouncementQueryRepository announcementRepository;
    private final AnnouncementRepositorySupport announcementRepositorySupport;
    private final AnnouncementDtoMapper announcementDtoMapper;

    // 상세 조회
    @Override
    public Announcement findAnnouncementById(Long id) {
        return announcementRepository.findAnnouncementById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ANNOUNCEMENT_NOT_FOUND));
    }

    // 목록 조회
    @Override
    public Page<AnnouncementSummary> findAnnouncementSummaries(
            Pageable pageable,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        return announcementRepositorySupport.findFilteredAnnouncements(
                pageable,
                startDate,
                endDate
        );
    }
}