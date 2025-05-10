package com.example.dgu_semi_erp_back.usecase.budget;

import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.projection.budget.BudgetPlanProjection.BudgetPlanSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface FindFilteredBudgetPlansUseCase {
    Page<BudgetPlanSummaryProjection> findSummaryByFilter(Pageable pageable,
                                                          String executeType,
                                                          String clubName,
                                                          String content,
                                                          String drafter,
                                                          LocalDateTime expectedPaymentDate,
                                                          Integer paymentAmount,
                                                          LocalDateTime createdAt,
                                                          BudgetStatus status);
}