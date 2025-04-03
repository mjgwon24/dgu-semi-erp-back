package com.example.dgu_semi_erp_back.dto.budget;

import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;
import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.entity.budget.types.PaymentType;
import lombok.Builder;

import java.time.LocalDateTime;

public final class BudgetPlanCommandDto {
    private BudgetPlanCommandDto() {}

    @Builder
    public record BudgetPlanCreateRequest(
            String executeType,
            PaymentType paymentType,
            LocalDateTime paymentDate,
            String content,
            String author,
            int paymentAmount,
            String planReviewer,
            String planApprover
    ) {}

    @Builder
    public record BudgetPlanCreateResponse(
            BudgetPlan budgetPlan
    ) {}

    @Builder
    public record BudgetPlanUpdateRequest(
            String executeType,
            PaymentType paymentType,
            LocalDateTime paymentDate,
            String content,
            String author,
            int paymentAmount,
            String planReviewer,
            String planApprover,
            BudgetStatus status
    ) {}

    @Builder
    public record BudgetPlanUpdateResponse(
            BudgetPlan budgetPlan
    ) {}

}