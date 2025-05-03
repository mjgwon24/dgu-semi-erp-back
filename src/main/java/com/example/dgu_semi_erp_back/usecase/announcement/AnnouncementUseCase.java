package com.example.dgu_semi_erp_back.usecase.announcement;

import com.example.dgu_semi_erp_back.entity.announcement.Announcement;

public interface AnnouncementUseCase {
    Announcement findAnnouncementById(Long id);
}
