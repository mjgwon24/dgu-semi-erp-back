package com.example.dgu_semi_erp_back.api.budget;

import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanRejectRequest;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanCreateRequest;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanCreateResponse;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanUpdateResponse;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanUpdateRequest;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanQueryDto.BudgetPlanSearchResponse;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanQueryDto.BudgetPlanDetailResponse;
import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.mapper.BudgetPlanMapper;
import com.example.dgu_semi_erp_back.usecase.budget.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/budget/plan")
@Validated
public class BudgetPlanApi {
    private final CreateBudgetPlanUseCase createBudgetPlanUseCase;
    private final UpdateBudgetPlanUseCase updateBudgetPlanUseCase;
    private final ApproveBudgetPlanReviewUseCase approveBudgetPlanReviewUseCase;
    private final ApproveFinalBudgetPlanUseCase approveFinalBudgetPlanUseCase;
    private final RejectBudgetPlanUseCase rejectBudgetPlanUseCase;
    private final FindBudgetPlanUseCase findBudgetPlanUseCase;
    private final FindFilteredBudgetPlansUseCase findFilteredBudgetPlansUseCase;
    private final BudgetPlanMapper budgetPlanMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetPlanCreateResponse create(
            @RequestBody @Valid BudgetPlanCreateRequest request
    ) {
        var budgetPlan = createBudgetPlanUseCase.create(request);

        return budgetPlanMapper.toCreateResponse(budgetPlan);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetPlanUpdateResponse update(
            @PathVariable Long id,
            @RequestBody @Valid BudgetPlanUpdateRequest request
    ) {
        var updatedBudgetPlan = updateBudgetPlanUseCase.update(id, request);

        return budgetPlanMapper.toUpdateResponse(updatedBudgetPlan);
    }

    @PatchMapping("/{id}/review/approve")
    @ResponseStatus(HttpStatus.OK)
    public BudgetPlanUpdateResponse approveReview(
            @PathVariable Long id,
            @RequestParam String reviewer
    ) {
        var approvedBudgetPlan = approveBudgetPlanReviewUseCase.approveReview(id, reviewer);
        return budgetPlanMapper.toUpdateResponse(approvedBudgetPlan);
    }

    @PatchMapping("/{id}/final/approve")
    @ResponseStatus(HttpStatus.OK)
    public BudgetPlanUpdateResponse approveFinal(
            @PathVariable Long id,
            @RequestParam String approver
    ) {
        var approvedBudgetPlan = approveFinalBudgetPlanUseCase.approveFinal(id, approver);
        return budgetPlanMapper.toUpdateResponse(approvedBudgetPlan);
    }

    @PatchMapping("/{id}/reject")
    @ResponseStatus(HttpStatus.OK)
    public BudgetPlanUpdateResponse reject(
            @PathVariable Long id,
            @RequestBody BudgetPlanRejectRequest budgetPlanRejectRequest
    ) {
        var rejectedBudgetPlan = rejectBudgetPlanUseCase.reject(id, budgetPlanRejectRequest);
        return budgetPlanMapper.toUpdateResponse(rejectedBudgetPlan);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetPlanDetailResponse getBudgetPlanById(@PathVariable Long id) {
        return budgetPlanMapper.toDetailResponse(findBudgetPlanUseCase.findById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BudgetPlanSearchResponse getFilteredBudgetPlans(
            @PageableDefault(size = 5) Pageable pageable,
            @RequestParam(required = false) String executeType,
            @RequestParam(required = false) String clubName,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String drafter,
            @RequestParam(required = false) LocalDateTime expectedPaymentDate,
            @RequestParam(required = false) Integer paymentAmount,
            @RequestParam(required = false) LocalDateTime createdAt,
            @RequestParam(required = false) BudgetStatus status
    ) {
        var filteredBudgetPlanPage = findFilteredBudgetPlansUseCase.findSummaryByFilter(
                pageable,
                executeType,
                clubName,
                content,
                drafter,
                expectedPaymentDate,
                paymentAmount,
                createdAt,
                status
        );

        var budgetPlanResponses = budgetPlanMapper.toSummaryResponseList(filteredBudgetPlanPage.getContent());

        return BudgetPlanSearchResponse.builder()
                .content(budgetPlanResponses)
                .pageNumber(filteredBudgetPlanPage.getNumber())
                .pageSize(filteredBudgetPlanPage.getSize())
                .totalElements(filteredBudgetPlanPage.getTotalElements())
                .totalPages(filteredBudgetPlanPage.getTotalPages())
                .last(filteredBudgetPlanPage.isLast())
                .build();
    }
}
