package com.example.dgu_semi_erp_back.service.budget;

import com.example.dgu_semi_erp_back.common.exception.CustomException;
import com.example.dgu_semi_erp_back.common.exception.ErrorCode;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanRejectRequest;
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
import com.example.dgu_semi_erp_back.usecase.budget.*;
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
        ApproveFinalBudgetPlanUseCase,
        RejectBudgetPlanUseCase {
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
                .budgetPlan(existingPlan)
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
            throw new CustomException(ErrorCode.INVALID_REVIEW_APPROVAL_STATUS);
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
            throw new CustomException(ErrorCode.UNAUTHORIZED_APPROVAL);
        }

        // 예산 계획 상태 확인 (검토 완료 상태가 아니면 예외 발생)
        if (budgetPlan.getStatus() != BudgetStatus.REVIEWED) {
            throw new CustomException(ErrorCode.INVALID_FINAL_APPROVAL_STATUS);
        }

        // 예산 계획 상태를 '승인 완료(ACCEPTED)'로 변경
        budgetPlan.updateStatus(BudgetStatus.ACCEPTED);

        // 예산 사용 내역(budgetUsage) 생성 및 저장
        LocalDateTime now = LocalDateTime.now();
        BudgetUsage budgetUsage = budgetUsageMapper.toEntity(budgetPlan, now, now);
        budgetUsageCommandRepository.save(budgetUsage);

        return budgetPlan;
    }

    @Transactional
    @Override
    public BudgetPlan reject(Long budgetPlanId, BudgetPlanRejectRequest req) {
        // 예산안 조회 (없으면 예외)
        BudgetPlan plan = budgetPlanRepository.findById(budgetPlanId)
                .orElseThrow(() -> new CustomException(ErrorCode.BUDGET_PLAN_NOT_FOUND));

        // 예산안 상태 확인
        BudgetStatus status = plan.getStatus();

        // reviewer/approver 정보 유효성 검사
        boolean isReviewer = req.reviewer() != null && !req.reviewer().isBlank();
        boolean isApprover = req.approver() != null && !req.approver().isBlank();

        // 권한 정보가 모두 없는 경우
        if (!isReviewer && !isApprover) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        // reviewer와 approver가 동시에 입력된 경우
        if (isReviewer && isApprover) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        // reviewer 정보 불일치
        if (isReviewer && !plan.getPlanReviewer().equals(req.reviewer())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REVIEWER);
        }

        // approver 정보 불일치
        if (isApprover && !plan.getPlanApprover().equals(req.approver())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_APPROVAL);
        }

        // 이미 승인된 예산안은 반려 불가
        if (status == BudgetStatus.ACCEPTED) {
            throw new CustomException(ErrorCode.REJECT_ON_ACCEPTED);
        }

        // 이미 반려된 예산안은 재반려 불가
        if (status == BudgetStatus.REJECTED) {
            throw new CustomException(ErrorCode.REJECT_ON_REJECTED);
        }

        // reviewer가 HOLD 또는 REVIEWED 상태에서 반려하는 경우
        if (isReviewer && (status == BudgetStatus.HOLD || status == BudgetStatus.REVIEWED)) {
            plan.updateStatus(BudgetStatus.REJECTED);
            return plan;
        }

        // approver가 REVIEWED 상태에서 반려하는 경우
        if (isApprover && status == BudgetStatus.REVIEWED) {
            plan.updateStatus(BudgetStatus.REJECTED);
            return plan;
        }

        // 그 외의 경우 반려 불가
        throw new CustomException(ErrorCode.REJECT_NOT_ALLOWED);
    }
}
