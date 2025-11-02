package com.example.dgu_semi_erp_back.usecase.announcement;

import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementCommandDto.AnnouncementCreateRequest;
import com.example.dgu_semi_erp_back.entity.announcement.Announcement;

public interface CreateAnnouncementUseCase {
    Announcement create(AnnouncementCreateRequest request);
}
