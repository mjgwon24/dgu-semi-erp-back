package com.example.dgu_semi_erp_back.dto.budget;

import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;
import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.entity.budget.types.PaymentType;
import lombok.Builder;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public final class BudgetPlanCommandDto {
    private BudgetPlanCommandDto() {}

    @Builder
    public record BudgetPlanCreateRequest(
            String executeType,
            String clubName,
            PaymentType paymentType,
            String content,
            String author,
            LocalDateTime expectedPaymentDate,
            int paymentAmount,
            String planReviewer,
            String planApprover
    ) {}

    @Builder
    public record BudgetPlanCreateResponse(
            Long id,
            String executeType,
            String clubName,
            PaymentType paymentType,
            String content,
            String author,
            LocalDateTime expectedPaymentDate,
            int paymentAmount,
            BudgetStatus status,
            String planReviewer,
            String planApprover,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record BudgetPlanUpdateRequest(
            Long id,
            String executeType,
            PaymentType paymentType,
            String content,
            String author,
            LocalDateTime expectedPaymentDate,
            int paymentAmount,
            String planReviewer,
            String planApprover,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record BudgetPlanUpdateResponse(
            Long id,
            String executeType,
            String clubName,
            PaymentType paymentType,
            String content,
            String author,
            LocalDateTime expectedPaymentDate,
            int paymentAmount,
            BudgetStatus status,
            String planReviewer,
            String planApprover,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

}