package com.example.dgu_semi_erp_back.service.budget;

import com.example.dgu_semi_erp_back.common.exception.CustomException;
import com.example.dgu_semi_erp_back.common.exception.ErrorCode;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanCreateRequest;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanUpdateRequest;
import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;
import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.mapper.BudgetDtoMapper;
import com.example.dgu_semi_erp_back.repository.budget.BudgetPlanQueryRepository;
import com.example.dgu_semi_erp_back.repository.club.ClubRepository;
import com.example.dgu_semi_erp_back.usecase.budget.CreateBudgetPlanUseCase;
import com.example.dgu_semi_erp_back.usecase.budget.UpdateBudgetPlanUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BudgetPlanCommandService implements CreateBudgetPlanUseCase, UpdateBudgetPlanUseCase {
    private final BudgetPlanQueryRepository budgetPlanRepository;
    private final ClubRepository clubRepository;
    private final BudgetDtoMapper mapper;

    @Transactional
    @Override
    public BudgetPlan create(BudgetPlanCreateRequest request) {
        LocalDateTime now = LocalDateTime.now();

        Club club = clubRepository.findByName(request.clubName())
                .orElseThrow(() -> new IllegalArgumentException("Club not found"));

        BudgetPlan budgetPlan = mapper.toEntity(request, club, BudgetStatus.HOLD, now, now);

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
