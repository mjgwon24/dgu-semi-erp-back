package com.example.dgu_semi_erp_back.entity;

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
    private int announcement_id;

    @JoinColumn(name = "club_id")
    private int club_id;

    @Column(nullable = false)
    private LocalDateTime create_at;
    @Column(nullable = false)
    private LocalDateTime update_at;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

}
