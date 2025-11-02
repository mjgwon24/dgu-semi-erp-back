package com.example.dgu_semi_erp_back.service.announcement;

import com.example.dgu_semi_erp_back.common.exception.CustomException;
import com.example.dgu_semi_erp_back.common.exception.ErrorCode;
import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementCommandDto.AnnouncementUpdateRequest;
import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementCommandDto.AnnouncementCreateRequest;
import com.example.dgu_semi_erp_back.entity.announcement.Announcement;
import com.example.dgu_semi_erp_back.mapper.AnnouncementDtoMapper;
import com.example.dgu_semi_erp_back.repository.announcement.AnnouncementCommandRepository;
import com.example.dgu_semi_erp_back.repository.announcement.AnnouncementQueryRepository;
import com.example.dgu_semi_erp_back.usecase.announcement.CreateAnnouncementUseCase;
import com.example.dgu_semi_erp_back.usecase.announcement.UpdateAnnouncementUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnnouncementCommandService implements
        CreateAnnouncementUseCase,
        UpdateAnnouncementUseCase {
    private final AnnouncementDtoMapper announcementDtoMapper;
    private final AnnouncementQueryRepository announcementQueryRepository;
    private final AnnouncementCommandRepository announcementCommandRepository;

    @Transactional
    @Override
    public Announcement create(AnnouncementCreateRequest request){
        LocalDateTime now = LocalDateTime.now();
        Announcement announcement = announcementDtoMapper.toEntity(request, now, now);
        return announcementCommandRepository.save(announcement);
    }

    @Transactional
    @Override
    public Announcement update(Long announcementId, AnnouncementUpdateRequest request) {
        // 기존에 있었는지 확인
        Announcement existingAnnouncement = announcementQueryRepository.findById(announcementId)
                .orElseThrow(() -> new CustomException(ErrorCode.ANNOUNCEMENT_NOT_FOUND));

        LocalDateTime now = LocalDateTime.now();

        existingAnnouncement.Update(request, existingAnnouncement, now);
        return existingAnnouncement;
    }
}
