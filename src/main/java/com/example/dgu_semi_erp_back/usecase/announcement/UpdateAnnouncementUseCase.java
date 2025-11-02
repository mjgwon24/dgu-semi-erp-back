package com.example.dgu_semi_erp_back.usecase.announcement;

import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementCommandDto;
import com.example.dgu_semi_erp_back.entity.announcement.Announcement;

public interface UpdateAnnouncementUseCase {
    Announcement update(Long Id, AnnouncementCommandDto.AnnouncementUpdateRequest request);
}
