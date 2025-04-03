package com.example.dgu_semi_erp_back.projection.budget;

import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.entity.budget.types.PaymentType;
import lombok.Builder;

import java.time.LocalDateTime;

public final class BudgetPlanProjection {
    @Builder
    public record BudgetPlanSummary(
            Long id,
            String executeType,
            LocalDateTime paymentDate,
            String content,
            String author,
            int paymentAmount,
            BudgetStatus status
    ) {}

    @Builder
    public record BudgetPlanDetail(
            Long id,
            String executeType,
            PaymentType paymentType,
            LocalDateTime paymentDate,
            String content,
            String author,
            int paymentAmount,
            String planReviewer,
            String planApprover,
            BudgetStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}
