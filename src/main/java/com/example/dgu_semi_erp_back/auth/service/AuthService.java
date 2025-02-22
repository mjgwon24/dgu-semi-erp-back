package com.example.dgu_semi_erp_back.auth.service;

import com.example.dgu_semi_erp_back.auth.dto.SignInRequest;
import com.example.dgu_semi_erp_back.auth.dto.SignUpRequest;
import com.example.dgu_semi_erp_back.auth.dto.SignUpResponse;
import com.example.dgu_semi_erp_back.auth.dto.TokenResponse;
import com.example.dgu_semi_erp_back.auth.entity.RefreshToken;
import com.example.dgu_semi_erp_back.auth.entity.User;
import com.example.dgu_semi_erp_back.auth.repository.RefreshTokenRepository;
import com.example.dgu_semi_erp_back.auth.repository.UserRepository;
import com.example.dgu_semi_erp_back.common.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        // 새로운 사용자 객체 생성
        User newUser = User.builder()
                .username(request.username())
                .password(BCrypt.hashpw(request.password(), BCrypt.gensalt()))
                .email(request.email())
                .nickname(request.nickname())
                .role(request.role())
                .build();

        // 사용자 정보 저장(DB)
        User userPs = userRepository.save(newUser);

        return SignUpResponse.builder()
                .userName(userPs.getUsername())
                .nickName(userPs.getNickname())
                .message("회원가입이 완료되었습니다.")
                .build();
    }

    @Transactional
    public TokenResponse signIn(SignInRequest request) {
        // 사용자 이름으로 DB에서 사용자 조회
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        // 입력된 비밀번호와 저장된 비밀번호 비교
        if (!new BCryptPasswordEncoder().matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 주어진 사용자에 대한 리프레시 토큰이 DB에 존재하는지 확인하고, 존재할 경우 해당 리프레시 토큰 삭제(DB)
        Optional<RefreshToken> existingRefreshToken = refreshTokenRepository.findByUser(user);
        existingRefreshToken.ifPresent(refreshTokenRepository::delete);

        // 새로운 액세스 토큰 생성
        String accessToken = jwtUtil.createAccessToken(user);

        // 새로운 리프레시 토큰 생성
        String refreshToken = jwtUtil.createRefreshToken(user);

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .user(user)
                .token(refreshToken)
                .build();

        // 리프레시 토큰 저장(DB)
        refreshTokenRepository.save(refreshTokenEntity);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public TokenResponse reissueRefreshToken(String token) {
        // 리프레시 토큰이 DB에 존재하는지 확인하고, 존재하지 않으면 예외를 발생
        RefreshToken existingRefreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new NoSuchElementException("리프레시 토큰이 존재하지 않습니다."));

        // 사용자 정보 추출
        User user = existingRefreshToken.getUser();

        // 새로운 액세스 토큰 및 리프레시 토큰 생성
        String newAccessToken = jwtUtil.createAccessToken(user);
        String newRefreshToken = jwtUtil.createRefreshToken(user);

        RefreshToken createdRefreshToken = RefreshToken.builder()
                .user(user)
                .token(newRefreshToken)
                .build();

        // 리프레시 토큰 저장(DB)
        refreshTokenRepository.save(createdRefreshToken);
        // 기존 리프레시 토큰 삭제(DB)
        refreshTokenRepository.delete(existingRefreshToken);

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
