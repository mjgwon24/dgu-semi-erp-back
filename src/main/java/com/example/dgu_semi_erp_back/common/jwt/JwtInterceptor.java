package com.example.dgu_semi_erp_back.common.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization"); // Authorization 헤더에서 토큰 추출

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 부분 제거
            if (jwtUtil.isTokenValid(token)) {
                // 사용자 정보 추출 및 request에 저장
                String username = jwtUtil.getUsernameFromToken(token);
                request.setAttribute("username", username);

                // 토큰이 유효하면 요청을 계속 진행
                return true;
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized 응답
        return false; // 토큰이 없으면 요청 차단
    }
}