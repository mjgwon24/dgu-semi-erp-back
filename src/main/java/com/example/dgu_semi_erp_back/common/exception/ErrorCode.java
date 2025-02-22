package com.example.dgu_semi_erp_back.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATED_MEMBER(HttpStatus.BAD_REQUEST, "001_DUPLICATED_EMAIL", "이미 가입된 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "002_USER_NOT_FOUND", "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "003_INVALID_PASSWORD", "비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "004_UNAUTHORIZED_ACCESS", "접근 권한이 없습니다."),
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "005_TOO_MANY_REQUESTS", "요청 한도를 초과했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}