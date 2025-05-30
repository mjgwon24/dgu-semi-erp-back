package com.example.dgu_semi_erp_back.repository.announcement;

import com.example.dgu_semi_erp_back.entity.announcement.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

// Post, DELETE 용
public interface AnnouncementCommandRepository extends JpaRepository<Announcement, Long> {
}