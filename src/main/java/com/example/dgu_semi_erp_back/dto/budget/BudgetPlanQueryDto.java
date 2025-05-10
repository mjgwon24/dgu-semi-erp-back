package com.example.dgu_semi_erp_back.dto.budget;

import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.entity.budget.types.PaymentType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public final class BudgetPlanQueryDto {
    private BudgetPlanQueryDto() {}

    @Builder
    public record BudgetPlanDetailResponse(
            Long id,
            String executeType,
            String clubName,
            PaymentType paymentType,
            String content,
            String drafter,
            LocalDateTime expectedPaymentDate,
            Integer paymentAmount,
            BudgetStatus status,
            String planReviewer,
            String planApprover,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record BudgetPlanSearchResponse(
            List<BudgetPlanSummaryResponse> content,
            Integer pageNumber,
            Integer pageSize,
            Long totalElements,
            Integer totalPages,
            Boolean last
    ) {
        @Builder
        public record BudgetPlanSummaryResponse(
                Long id,
                String executeType,
                String clubName,
                String content,
                String drafter,
                LocalDateTime expectedPaymentDate,
                Integer paymentAmount,
                LocalDateTime createdAt,
                BudgetStatus status
        ) {}
    }
}
