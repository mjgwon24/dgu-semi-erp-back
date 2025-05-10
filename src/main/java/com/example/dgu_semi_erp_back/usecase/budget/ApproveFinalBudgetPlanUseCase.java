package com.example.dgu_semi_erp_back.usecase.budget;

import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;

public interface ApproveFinalBudgetPlanUseCase {
    BudgetPlan approveFinal(Long id, String approver);
}
