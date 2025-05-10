package com.example.dgu_semi_erp_back.usecase.budget;

import com.example.dgu_semi_erp_back.projection.budget.BudgetPlanProjection.BudgetPlanDetailProjection;

public interface FindBudgetPlanUseCase {
    BudgetPlanDetailProjection findById(Long id);
}
