package com.example.dgu_semi_erp_back.dto.budgetplan;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BudgetPlanCreateRequest(
        String executeType,
        String paymentType,
        LocalDateTime paymentDate,
        String content,
        String author,
        int paymentAmount,
        String planReviewer,
        String planApprover
) {}