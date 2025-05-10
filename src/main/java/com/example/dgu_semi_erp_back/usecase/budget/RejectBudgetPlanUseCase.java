package com.example.dgu_semi_erp_back.usecase.budget;

import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto;
import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;

public interface RejectBudgetPlanUseCase {
    BudgetPlan reject(Long id, BudgetPlanCommandDto.BudgetPlanRejectRequest budgetPlanRejectRequest);
}
