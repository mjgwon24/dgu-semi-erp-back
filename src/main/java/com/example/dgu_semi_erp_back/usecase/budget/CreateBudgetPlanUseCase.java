package com.example.dgu_semi_erp_back.usecase.budget;

import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanCreateRequest;
import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;

public interface CreateBudgetPlanUseCase {
    BudgetPlan create(BudgetPlanCreateRequest request);
}
