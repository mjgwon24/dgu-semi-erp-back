package com.example.dgu_semi_erp_back.auth.service;

import com.example.dgu_semi_erp_back.auth.entity.RefreshTokenHistory;
import com.example.dgu_semi_erp_back.auth.repository.RefreshTokenHistoryRepository;
import com.example.dgu_semi_erp_back.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenManagementService {
    private final RefreshTokenHistoryRepository refreshTokenHistoryRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleRefreshTokenReuse(RefreshTokenHistory refreshTokenHistory) {
        // 재사용 횟수 증가 및 리프레시 토큰 기록 저장
        refreshTokenHistory.incrementReuseCount();
        refreshTokenHistoryRepository.save(refreshTokenHistory);

        // 해당 유저의 모든 리프레시 토큰 삭제
        refreshTokenRepository.deleteAllByUserId(refreshTokenHistory.getUser().getId());
    }
}
