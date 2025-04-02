package com.example.dgu_semi_erp_back.service.budget;

import com.example.dgu_semi_erp_back.common.exception.CustomException;
import com.example.dgu_semi_erp_back.common.exception.ErrorCode;
import com.example.dgu_semi_erp_back.dto.budget.BudgetCommandDto.*;
import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;
import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.mapper.BudgetDtoMapper;
import com.example.dgu_semi_erp_back.repository.budget.BudgetPlanRepository;
import com.example.dgu_semi_erp_back.usecase.budget.BudgetCreateUseCase;
import com.example.dgu_semi_erp_back.usecase.budget.BudgetUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BudgetCommandService implements BudgetCreateUseCase, BudgetUpdateUseCase {
    private final BudgetPlanRepository budgetPlanRepository;
    private final BudgetDtoMapper mapper;

    @Override
    public BudgetPlan create(BudgetPlanCreateRequest request) {
        LocalDateTime now = LocalDateTime.now();

        BudgetPlan budgetPlan = mapper.toEntity(request, BudgetStatus.HOLD, now, now);

        return budgetPlanRepository.save(budgetPlan);
    }

    @Transactional
    @Override
    public BudgetPlan update(Long id, BudgetPlanUpdateRequest request) {
        BudgetPlan existingPlan = budgetPlanRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BUDGET_PLAN_NOT_FOUND));

        LocalDateTime now = LocalDateTime.now();

        existingPlan.prepareUpdate()
                .request(request)
                .updatedAt(now)
                .update();

        return existingPlan;
    }
}
