package com.example.dgu_semi_erp_back.repository.announcement;

import com.example.dgu_semi_erp_back.entity.announcement.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

// 생성, 변경, 삭제용
public interface AnnouncementCommandRepository extends JpaRepository<Announcement, Long> {
}