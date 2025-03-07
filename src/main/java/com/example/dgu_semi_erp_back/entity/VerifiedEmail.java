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
public class VerifiedEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email; // 인증할 이메일

    @Column(nullable = false)
    private String otp; // 6자리 OTP 코드

    @Column(nullable = false)
    private String otpRequestToken; // OTP 요청 시 발급되는 토큰

    @Column(nullable = false)
    private LocalDateTime otpExpiry; // OTP 만료 시간

    @Column(nullable = true)
    private String otpVerificationToken; // OTP 검증 후 발급되는 토큰
}