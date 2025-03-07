package com.example.dgu_semi_erp_back.api;

import com.example.dgu_semi_erp_back.service.AuthService;
import com.example.dgu_semi_erp_back.dto.SignInRequest;
import com.example.dgu_semi_erp_back.dto.SignUpRequest;
import com.example.dgu_semi_erp_back.dto.TokenResponse;
import com.example.dgu_semi_erp_back.dto.VerifyOtpRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApi {
    private final AuthService authService;

    @PostMapping("/request-otp")
    public ResponseEntity<String> requestOtp(@RequestParam String email) throws Exception {
        var otpRequestToken = authService.sendOtp(email);
        return ResponseEntity.ok(otpRequestToken);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest) {
        var otpVerificationToken = authService.verifyOtp(verifyOtpRequest);
        return ResponseEntity.ok(otpVerificationToken);
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
        authService.registerUser(signUpRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/signIn")
    public ResponseEntity<TokenResponse> signIn(@RequestBody SignInRequest request, HttpServletResponse response) {
        // 사용자 인증 후 액세스 토큰 및 리프레시 토큰 생성
        TokenResponse tokenResponse = authService.signIn(request);

        // 리프레시 토큰을 쿠키에 저장
        Cookie cookie = createCookie(tokenResponse.refreshToken());
        // 응답에 쿠키 추가
        response.addCookie(cookie);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(TokenResponse.builder()
                        .accessToken(tokenResponse.accessToken())
                        .build());
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<TokenResponse> reissueRefreshToken(@RequestBody String token, HttpServletResponse response) {
        // 리프레시 토큰이 DB에 존재하는지 확인하고, 존재할 경우 새로운 액세스 토큰과 리프레시 토큰을 생성
        TokenResponse tokenResponse = authService.reissueRefreshToken(token);

        // 리프레시 토큰을 쿠키에 저장
        Cookie cookie = createCookie(tokenResponse.refreshToken());
        // 응답에 쿠키 추가
        response.addCookie(cookie);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(TokenResponse.builder()
                        .accessToken(tokenResponse.accessToken())
                        .build());
    }

    private Cookie createCookie(String refreshToken) {
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setHttpOnly(true); // HTTP Only 옵션 설정
        cookie.setSecure(true); // Secure 옵션 설정 (HTTPS 환경에서만 전송)
        cookie.setPath("/"); // 쿠키가 유효한 경로 설정
        cookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키의 유효 기간 설정 (예: 7일)

        return cookie;
    }
}
