package com.example.dgu_semi_erp_back.entity.Announcement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Announcement {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int announcementId;

    @JoinColumn(name = "clubId")
    private int clubId;

    @Column(nullable = false)
    private LocalDateTime createAt;
    @Column(nullable = false)
    private LocalDateTime updateAt;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

}
