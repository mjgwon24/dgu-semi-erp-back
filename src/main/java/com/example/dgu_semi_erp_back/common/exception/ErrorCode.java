package com.example.dgu_semi_erp_back.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATED_MEMBER(HttpStatus.BAD_REQUEST, "DUPLICATED_EMAIL", "이미 가입된 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "INVALID_PASSWORD", "비밀번호가 일치하지 않습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", "정상적인 요청이 아닙니다"),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED_ACCESS", "접근 권한이 없습니다."),
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "TOO_MANY_REQUESTS", "요청 한도를 초과했습니다."),
    BUDGET_PLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "BUDGET_PLAN_NOT_FOUND", "예산 계획을 찾을 수 없습니다."),
    ANNOUNCEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "ANNOUNCEMENT_NOT_FOUND", "공지사항을 찾을 수 없습니다."),
    UNAUTHORIZED_REVIEWER(HttpStatus.FORBIDDEN, "UNAUTHORIZED_REVIEWER", "검토 권한이 없습니다."),
    UNAUTHORIZED_APPROVAL(HttpStatus.FORBIDDEN, "UNAUTHORIZED_APPROVAL", "승인 권한이 없습니다."),
    UNAUTHORIZED_REQUEST(HttpStatus.FORBIDDEN, "UNAUTHORIZED_REQUEST", "요청 권한이 없습니다."),
    UNAUTHORIZED_MEMBER(HttpStatus.FORBIDDEN, "UNAUTHORIZED_MEMBER", "요청자가 해당 동아리에서 활성화 상태가 아닙니다."),
    UNAUTHORIZED_CLUB_MEMBER(HttpStatus.FORBIDDEN, "UNAUTHORIZED_CLUB_MEMBER", "요청한 사용자가 동아리에 소속되어 있지 않습니다."),
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "UNAUTHORIZED_USER", "요청한 사용자의 권한이 부족합니다."),
    UNAUTHORIZED_AUTHOR(HttpStatus.FORBIDDEN, "UNAUTHORIZED_AUTHOR", "기안자만 거절할 수 있습니다."),
    INVALID_REVIEW_APPROVAL_STATUS(HttpStatus.BAD_REQUEST, "INVALID_REVIEW_STATUS", "기안 상태에서만 검토 승인할 수 있습니다."),
    INVALID_FINAL_APPROVAL_STATUS(HttpStatus.BAD_REQUEST, "INVALID_APPROVAL_STATUS", "검토 완료 상태에서만 최종 승인할 수 있습니다."),
    REJECT_ON_ACCEPTED(HttpStatus.BAD_REQUEST, "REJECT_ON_ACCEPTED", "승인된 예산 계획은 거절할 수 없습니다."),
    REJECT_ON_REJECTED(HttpStatus.BAD_REQUEST, "REJECT_ON_REJECTED", "이미 거절된 예산 계획입니다."),
    REJECT_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "REJECT_NOT_ALLOWED", "해당 상태에서는 거절이 허용되지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}