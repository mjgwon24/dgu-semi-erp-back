package com.example.dgu_semi_erp_back.dto.budgetplan;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BudgetPlanSearchCondition(
        String executeType,
        String content,
        String status,
        String author,
        LocalDate startDate,
        LocalDate endDate,
        Integer minAmount,
        Integer maxAmount
) {}
