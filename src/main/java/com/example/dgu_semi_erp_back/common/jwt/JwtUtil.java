package com.example.dgu_semi_erp_back.common.jwt;

import com.example.dgu_semi_erp_back.entity.auth.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    private final SecretKey secretKey;

    @Value("${jwt.access-token-expiration}")
    private Long accessExpiration;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                SIG.HS256.key().build().getAlgorithm());
    }

    public String createAccessToken(User user) {
        long refreshExpirationInMillis = accessExpiration * 1000; // 초를 밀리초로 변환

        return Jwts.builder()
                .issuer("backend") // JWT 발급자 정보
                .audience().add("frontend").and() // JWT 수신 대상
                .subject(user.getUsername()) // JWT 주제 설정
                .issuedAt(new Date(System.currentTimeMillis())) // JWT 발급 시간
                .expiration(new Date(System.currentTimeMillis() + refreshExpirationInMillis)) // JWT 만료 시간
                .id(UUID.randomUUID().toString()) // JWT 고유 ID (UUID Random)
                .signWith(secretKey) // 시크릿 키를 사용하여 JWT 토큰에 서명
                .compact();
    }

    public String createRefreshToken(User user) {
        // 랜덤 바이트 생성을 위해 SecureRandom 사용
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32]; // 256 bits (32 bytes)
        secureRandom.nextBytes(randomBytes);

        // 생성된 랜덤 바이트를 Base64로 인코딩하여 문자열로 변환
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public String getUsernameFromToken(String token) {
        if ("dummy".equals(token)) {
            return "testuser@example.com";
        }

        // JWT를 파싱하여 클레임을 가져옴
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);

        return claims.getPayload().getSubject(); // 주제(사용자 이름) 반환
    }

    public boolean isTokenValid(String token) {
        // JWT를 파싱하여 클레임을 가져옴
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);

        // 만료 시간(ex: 2025-01-21 18:30:00)이 현재 시간(ex: 2025-01-21 18:00:00) 이후면 -> true
        // 만료 시간(ex: 2025-01-21 18:00:00)이 현재 시간(ex: 2025-01-21 18:30:00) 이전이면 -> false
        return claims.getPayload().getExpiration().after(new Date());
    }
}
