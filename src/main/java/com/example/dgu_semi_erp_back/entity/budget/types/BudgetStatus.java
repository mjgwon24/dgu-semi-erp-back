package com.example.dgu_semi_erp_back.entity.budget.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BudgetStatus {
    HOLD("기안"),
    REVIEWED("검토 완료"),
    ACCEPTED("승인 완료"),
    REJECTED("반려");

    private final String description;
}
