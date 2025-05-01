package com.example.dgu_semi_erp_back.service.announcement;

import com.example.dgu_semi_erp_back.common.exception.CustomException;
import com.example.dgu_semi_erp_back.common.exception.ErrorCode;
import com.example.dgu_semi_erp_back.entity.announcement.Announcement;
import com.example.dgu_semi_erp_back.repository.announcement.AnnouncementQueryRepository;
import com.example.dgu_semi_erp_back.usecase.announcement.AnnouncementUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnnouncementQueryService implements AnnouncementUseCase {
    private final AnnouncementQueryRepository announcementRepository;

    @Override
    public Announcement findAnnouncementById(Long id) {
        return announcementRepository.findAnnouncementById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ANNOUNCEMENT_NOT_FOUND));
    }
}