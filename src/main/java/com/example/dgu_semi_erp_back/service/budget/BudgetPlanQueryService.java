package com.example.dgu_semi_erp_back.service.budget;

import com.example.dgu_semi_erp_back.common.exception.CustomException;
import com.example.dgu_semi_erp_back.common.exception.ErrorCode;
import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.mapper.BudgetPlanMapper;
import com.example.dgu_semi_erp_back.projection.budget.BudgetPlanProjection.BudgetPlanSummary;
import com.example.dgu_semi_erp_back.projection.budget.BudgetPlanProjection.BudgetPlanDetail;
import com.example.dgu_semi_erp_back.repository.budget.BudgetPlanQueryRepository;
import com.example.dgu_semi_erp_back.repository.budget.BudgetPlanRepositorySupport;
import com.example.dgu_semi_erp_back.usecase.budget.FindFilteredBudgetPlansUseCase;
import com.example.dgu_semi_erp_back.usecase.budget.FindBudgetPlanUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BudgetPlanQueryService implements FindBudgetPlanUseCase, FindFilteredBudgetPlansUseCase {
    private final BudgetPlanQueryRepository budgetPlanRepository;
    private final BudgetPlanRepositorySupport budgetPlanRepositorySupport;
    private final BudgetPlanMapper mapper;

    @Override
    public BudgetPlanDetail findBudgetPlanById(Long id) {
        return budgetPlanRepository.findBudgetPlanById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BUDGET_PLAN_NOT_FOUND));
    }

    @Override
    public Page<BudgetPlanSummary> findFilteredBudgetPlans(
            Pageable pageable,
            String executeType,
            String clubName,
            String content,
            String author,
            LocalDateTime expectedPaymentDate,
            Integer paymentAmount,
            LocalDateTime createdAt,
            BudgetStatus status
    ) {
        return budgetPlanRepositorySupport.findFilteredBudgetPlans(
                pageable,
                executeType,
                clubName,
                content,
                author,
                expectedPaymentDate,
                paymentAmount,
                createdAt,
                status
        );
    }
}
