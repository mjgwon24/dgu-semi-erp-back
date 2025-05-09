package com.example.dgu_semi_erp_back.usecase.budget;

import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;

public interface ApproveBudgetPlanReviewUseCase {
    BudgetPlan approveReview(Long id, String reviewer);
}
