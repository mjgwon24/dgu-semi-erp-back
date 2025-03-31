package com.example.dgu_semi_erp_back.service;

import com.example.dgu_semi_erp_back.dto.budgetplan.*;
import com.example.dgu_semi_erp_back.entity.Budget.BudgetPlan;
import com.example.dgu_semi_erp_back.entity.Budget.BudgetStatus;
import com.example.dgu_semi_erp_back.mapper.BudgetPlanDtoMapper;
import com.example.dgu_semi_erp_back.repository.BudgetPlanRepository;
import com.example.dgu_semi_erp_back.repository.query.BudgetPlanQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetPlanService {

    private final BudgetPlanRepository budgetPlanRepository;
    private final BudgetPlanQueryRepository budgetPlanQueryRepository;
    private final BudgetPlanDtoMapper mapper;

    @Transactional
    public void createBudgetPlan(BudgetPlanCreateRequest request) {
        BudgetPlan plan = mapper.toEntity(request, BudgetStatus.HOLD, LocalDateTime.now());
        budgetPlanRepository.save(plan);
    }

    @Transactional(readOnly = true)
    public BudgetPlanReadResponse getBudgetPlan(Long id) {
        BudgetPlan plan = budgetPlanRepository.findById(id)
                .filter(p -> true)
                .orElseThrow(() -> new IllegalArgumentException("예산안이 존재하지 않습니다."));
        return mapper.toReadResponse(plan);
    }

    @Transactional(readOnly = true)
    public List<BudgetPlanReadResponse> searchBudgetPlans(BudgetPlanSearchCondition condition) {
        return budgetPlanQueryRepository.search(condition).stream()
                .map(mapper::toReadResponse)
                .toList();
    }

    @Transactional
    public void updateBudgetPlan(Long id, BudgetPlanUpdateRequest request) {
        BudgetPlan plan = budgetPlanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예산안이 존재하지 않습니다."));
        BudgetPlan updated = mapper.updateEntity(plan, request);
        budgetPlanRepository.save(updated);
    }

    @Transactional
    public void deleteBudgetPlan(Long id) {
        BudgetPlan plan = budgetPlanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예산안이 존재하지 않습니다."));
        budgetPlanRepository.delete(plan);
    }
}
