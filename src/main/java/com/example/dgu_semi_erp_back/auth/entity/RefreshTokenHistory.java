package com.example.dgu_semi_erp_back.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RefreshTokenHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;  // 사용된 리프레시 토큰

    @Column(nullable = false)
    private Instant usedAt; // 기존 리프레시 토큰이 삭제(사용)된 시간

    @Column(nullable = false)
    private Instant expiryDate; // 기존 리프레시 토큰 만료 시간

    @Column(nullable = false)
    private int reuseCount = 0; // 재사용 횟수 (기본값: 0)

    public void incrementReuseCount() {
        this.reuseCount++;
    }
}
