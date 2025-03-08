package com.example.dgu_semi_erp_back.common.ratelimiter;

import com.example.dgu_semi_erp_back.common.exception.CustomException;
import com.example.dgu_semi_erp_back.common.exception.ErrorCode;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RateLimiter {
    private static final int TOKEN_CONSUMPTION_COUNT = 1; // 요청 1건당 소비할 토큰 개수
    private final Bucket bucket; // Bucket4j에서 제공하는 속도 제한 객체 (Bucket4jConfig에서 주입됨)

    /**
     * 사용 가능한 토큰이 있는지 확인하고, 없으면 예외를 발생시킴.
     * 요청이 허용되면 토큰을 소비하고, 허용되지 않으면 TOO_MANY_REQUESTS 예외 발생.
     */
    public void verifyBucketAvailability() {
        if (bucket.tryConsume(TOKEN_CONSUMPTION_COUNT)) {  // 토큰 1개를 소비 시도
            return; // 토큰이 있다면 요청을 허용
        }

        // 토큰이 없으면 예외를 던져서 요청을 차단
        throw new CustomException(ErrorCode.TOO_MANY_REQUESTS);
    }
}
