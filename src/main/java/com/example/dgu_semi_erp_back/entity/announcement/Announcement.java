package com.example.dgu_semi_erp_back.entity.announcement;

import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementCommandDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private Long id;

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private String content; // 내용

    @Column(nullable = false)
    private String author; // 작성자

    private int viewCount; // 조회수

    private String file; // 배열로 변경 필요

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private Instant deletedAt;

    @Builder
    public void Update(AnnouncementCommandDto.AnnouncementUpdateRequest request, Announcement announcement, LocalDateTime updatedAt) {
        this.title = request.title();
        this.content = request.content();
        this.author = request.author();
        this.file = request.file();
        this.updatedAt = updatedAt;
    }

    public void markAsDeleted(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
}
