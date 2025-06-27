package com.example.dgu_semi_erp_back.usecase.announcement;

import com.example.dgu_semi_erp_back.entity.announcement.Announcement;

// Detail 조회때 쓰는 명세
public interface AnnouncementUseCase {
    Announcement findAnnouncementById(Long id);
}
