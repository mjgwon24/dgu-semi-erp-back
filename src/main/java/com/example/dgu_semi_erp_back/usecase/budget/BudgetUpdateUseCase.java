package com.example.dgu_semi_erp_back.usecase.budget;

import com.example.dgu_semi_erp_back.dto.budget.BudgetCommandDto.BudgetPlanUpdateRequest;
import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;

public interface BudgetUpdateUseCase {
    BudgetPlan update(Long id, BudgetPlanUpdateRequest request);
}
