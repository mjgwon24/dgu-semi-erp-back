package com.example.dgu_semi_erp_back.dto.budgetplan;

import lombok.Builder;

@Builder
public record BudgetPlanReadResponse(
        Long id,
        String executeType,
        String paymentType,
        String paymentDate,
        String content,
        String author,
        int paymentAmount,
        String planReviewer,
        String planApprover,
        String status,
        String createdAt
) {}