package com.example.dgu_semi_erp_back.service.budget;

import com.example.dgu_semi_erp_back.common.exception.CustomException;
import com.example.dgu_semi_erp_back.common.exception.ErrorCode;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanCreateRequest;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanUpdateRequest;
import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;
import com.example.dgu_semi_erp_back.entity.budget.BudgetUsage;
import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.mapper.BudgetPlanMapper;
import com.example.dgu_semi_erp_back.mapper.BudgetUsageMapper;
import com.example.dgu_semi_erp_back.repository.budget.BudgetPlanQueryRepository;
import com.example.dgu_semi_erp_back.repository.budget.BudgetUsageCommandRepository;
import com.example.dgu_semi_erp_back.repository.club.ClubRepository;
import com.example.dgu_semi_erp_back.usecase.budget.ApproveBudgetPlanReviewUseCase;
import com.example.dgu_semi_erp_back.usecase.budget.ApproveFinalBudgetPlanUseCase;
import com.example.dgu_semi_erp_back.usecase.budget.CreateBudgetPlanUseCase;
import com.example.dgu_semi_erp_back.usecase.budget.UpdateBudgetPlanUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BudgetPlanCommandService implements
        CreateBudgetPlanUseCase,
        UpdateBudgetPlanUseCase,
        ApproveBudgetPlanReviewUseCase,
        ApproveFinalBudgetPlanUseCase {
    private final BudgetPlanQueryRepository budgetPlanRepository;
    private final BudgetUsageCommandRepository budgetUsageCommandRepository;
    private final ClubRepository clubRepository;
    private final BudgetPlanMapper budgetPlanMapper;
    private final BudgetUsageMapper budgetUsageMapper;

    @Transactional
    @Override
    public BudgetPlan create(BudgetPlanCreateRequest request) {
        LocalDateTime now = LocalDateTime.now();

        Club club = clubRepository.findByName(request.clubName())
                .orElseThrow(() -> new IllegalArgumentException("Club not found"));

        BudgetPlan budgetPlan = budgetPlanMapper.toEntity(request, club, BudgetStatus.HOLD, now, now);
        return budgetPlanRepository.save(budgetPlan);
    }

    @Transactional
    @Override
    public BudgetPlan update(Long budgetPlanId, BudgetPlanUpdateRequest request) {
        BudgetPlan existingPlan = budgetPlanRepository.findById(budgetPlanId)
                .orElseThrow(() -> new CustomException(ErrorCode.BUDGET_PLAN_NOT_FOUND));

        LocalDateTime now = LocalDateTime.now();

        existingPlan.prepareUpdate()
                .request(request)
                .updatedAt(now)
                .update();
        return existingPlan;
    }

    @Transactional
    @Override
    public BudgetPlan approveReview(Long budgetPlanId, String reviewer) {
        // 예산 계획 조회
        BudgetPlan budgetPlan = budgetPlanRepository.findById(budgetPlanId)
                .orElseThrow(() -> new CustomException(ErrorCode.BUDGET_PLAN_NOT_FOUND));

        // 검토자 일치 여부 확인
        if (!budgetPlan.getPlanReviewer().equals(reviewer)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REVIEWER);
        }

        // 현재 상태가 '기안(HOLD)'인지 확인
        // BudgetStatus.HOLD: '기안' 상태, 검토 승인 전 단계
        if (budgetPlan.getStatus() != BudgetStatus.HOLD) {
            throw new CustomException(ErrorCode.INVALID_REVIEW_STATUS);
        }

        // 예산 계획 상태를 '검토 완료(REVIEWED)'로 변경
        budgetPlan.updateStatus(BudgetStatus.REVIEWED);
        return budgetPlan;
    }

    @Transactional
    @Override
    public BudgetPlan approveFinal(Long budgetPlanId, String approver) {
        // 예산 계획 조회
        BudgetPlan budgetPlan = budgetPlanRepository.findById(budgetPlanId)
                .orElseThrow(() -> new CustomException(ErrorCode.BUDGET_PLAN_NOT_FOUND));

        // 승인자 권한 확인
        if (!budgetPlan.getPlanApprover().equals(approver)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_APPROVER);
        }

        // 예산 계획 상태 확인 (검토 완료 상태가 아니면 예외 발생)
        if (budgetPlan.getStatus() != BudgetStatus.REVIEWED) {
            throw new CustomException(ErrorCode.INVALID_APPROVAL_STATUS);
        }

        // 예산 계획 상태를 '승인 완료(ACCEPTED)'로 변경
        budgetPlan.updateStatus(BudgetStatus.ACCEPTED);

        // 예산 사용 내역(budgetUsage) 생성 및 저장
        LocalDateTime now = LocalDateTime.now();
        BudgetUsage budgetUsage = budgetUsageMapper.toEntity(budgetPlan, now, now);
        budgetUsageCommandRepository.save(budgetUsage);

        return budgetPlan;
    }
}
