package com.example.dgu_semi_erp_back.entity.club;

/**
 * 동아리 내 역할
 * - priority 기반으로 권한 판별하므로, priority 순서 수정 금지
 */
public enum Role {
    MEMBER(1),        // 일반 회원
    TREASURER(2),     // 총무
    VICE_LEADER(3),   // 부회장
    LEADER(4);        // 동아리 회장

    private final int priority; // 권한 우선순위

    Role(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
