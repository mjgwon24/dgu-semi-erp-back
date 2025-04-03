package com.example.dgu_semi_erp_back.usecase.budget;

import com.example.dgu_semi_erp_back.dto.budget.BudgetCommandDto.*;
import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;

public interface BudgetPlanCreateUseCase {
    BudgetPlan create(BudgetPlanCreateRequest request);
}
