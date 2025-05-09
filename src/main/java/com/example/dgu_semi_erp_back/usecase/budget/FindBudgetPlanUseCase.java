package com.example.dgu_semi_erp_back.usecase.budget;

import com.example.dgu_semi_erp_back.projection.budget.BudgetPlanProjection.BudgetPlanDetail;

public interface FindBudgetPlanUseCase {
    BudgetPlanDetail findBudgetPlanById(Long id);
}
