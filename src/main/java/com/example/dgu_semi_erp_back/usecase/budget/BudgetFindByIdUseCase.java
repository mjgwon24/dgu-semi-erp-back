package com.example.dgu_semi_erp_back.usecase.budget;

import com.example.dgu_semi_erp_back.projection.budget.BudgetPlanProjection.BudgetPlanDetail;

import java.util.Optional;

public interface BudgetFindByIdUseCase {
    BudgetPlanDetail findBudgetPlanById(Long id);
}
