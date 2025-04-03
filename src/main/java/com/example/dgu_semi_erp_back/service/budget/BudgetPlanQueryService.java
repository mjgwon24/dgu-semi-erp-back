package com.example.dgu_semi_erp_back.service.budget;

import com.example.dgu_semi_erp_back.common.exception.CustomException;
import com.example.dgu_semi_erp_back.common.exception.ErrorCode;
import com.example.dgu_semi_erp_back.mapper.BudgetDtoMapper;
import com.example.dgu_semi_erp_back.projection.budget.BudgetPlanProjection.BudgetPlanDetail;
import com.example.dgu_semi_erp_back.repository.budget.BudgetPlanQueryRepository;
import com.example.dgu_semi_erp_back.usecase.budget.BudgetFindByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetPlanQueryService implements BudgetFindByIdUseCase {
    private final BudgetPlanQueryRepository budgetPlanRepository;
    private final BudgetDtoMapper mapper;

    @Override
    public BudgetPlanDetail findBudgetPlanById(Long id) {
        return budgetPlanRepository.findBudgetPlanById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BUDGET_PLAN_NOT_FOUND));
    }
}
