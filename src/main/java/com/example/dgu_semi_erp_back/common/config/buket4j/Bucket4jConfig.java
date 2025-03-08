package com.example.dgu_semi_erp_back.common.config.buket4j;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Bucket4jConfig {
    private static final int CAPACITY = 5; // 최대 5개의 요청을 허용 (버킷 크기)
    private static final int DURATION = 20; // 20초마다 토큰을 충전
    private static final int REFILL_TOKENS_COUNT = 5; // 한 번 충전 시 추가되는 토큰 개수

    /**
     * 요청 제한을 적용하기 위한 `Bucket` 객체를 생성하여 Spring Bean으로 등록합니다.
     * 이 `Bucket` 인스턴스는 `RateLimiter`에서 주입받아 사용됩니다.
     */
    @Bean
    public Bucket bucket() {
        // 설정된 주기(DURATION)마다 지정된 수의 토큰(REFILL_TOKENS_COUNT)을 충전하는 Refill 객체 생성
        Refill refill = Refill.intervally(
                REFILL_TOKENS_COUNT, // 60초마다 5개 충전
                Duration.ofSeconds(DURATION));

        // 버킷의 최대 용량(CAPACITY)과 충전 정책(refill)을 정의하는 Bandwidth 객체 생성
        Bandwidth limit = Bandwidth.classic(CAPACITY, refill);

        // 위에서 설정한 제한(Bandwidth)을 적용하여 Bucket 객체를 생성 및 반환
        return Bucket.builder()
                .addLimit(limit) // 제한 정책 적용
                .build();
    }
}
