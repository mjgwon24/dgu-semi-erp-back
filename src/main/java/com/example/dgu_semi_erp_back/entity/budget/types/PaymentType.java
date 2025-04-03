package com.example.dgu_semi_erp_back.entity.budget.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType {
    CARD("카드 결제"),
    BANKBOOK("통장 이체"),
    CASH("현금 결제");

    private final String description;
}
