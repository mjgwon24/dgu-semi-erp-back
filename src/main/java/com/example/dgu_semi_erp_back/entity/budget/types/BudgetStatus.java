package com.example.dgu_semi_erp_back.entity.budget.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BudgetStatus {
    HOLD("보류"),
    ACCEPTED("승인"),
    REJECTED("반려");

    private final String description;
}
