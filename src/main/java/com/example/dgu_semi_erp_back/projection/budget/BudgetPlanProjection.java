package com.example.dgu_semi_erp_back.projection.budget;

import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.entity.budget.types.PaymentType;
import com.example.dgu_semi_erp_back.entity.club.Club;
import lombok.Builder;

import java.time.LocalDateTime;

public final class BudgetPlanProjection {
    @Builder
    public record BudgetPlanSummaryProjection(
            Long id,
            String executeType,
            Club club,
            String content,
            String drafter,
            LocalDateTime expectedPaymentDate,
            int paymentAmount,
            LocalDateTime createdAt,
            BudgetStatus status
    ) {}

    @Builder
    public record BudgetPlanDetailProjection(
            Long id,
            String executeType,
            Club club,
            PaymentType paymentType,
            String content,
            String drafter,
            LocalDateTime expectedPaymentDate,
            int paymentAmount,
            BudgetStatus status,
            String planReviewer,
            String planApprover,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}
