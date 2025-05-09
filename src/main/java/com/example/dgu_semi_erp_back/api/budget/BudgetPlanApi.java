package com.example.dgu_semi_erp_back.api.budget;

import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanCreateRequest;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanCreateResponse;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanUpdateResponse;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanUpdateRequest;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanQueryDto;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanQueryDto.BudgetPlanSearchResponse.BudgetPlanSummaryResponse;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanQueryDto.BudgetPlanDetailResponse;
import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.mapper.BudgetDtoMapper;
import com.example.dgu_semi_erp_back.projection.budget.BudgetPlanProjection.BudgetPlanSummary;
import com.example.dgu_semi_erp_back.usecase.budget.CreateBudgetPlanUseCase;
import com.example.dgu_semi_erp_back.usecase.budget.FindFilteredBudgetPlansUseCase;
import com.example.dgu_semi_erp_back.usecase.budget.FindBudgetPlanUseCase;
import com.example.dgu_semi_erp_back.usecase.budget.UpdateBudgetPlanUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/budget/plan")
@Validated
public class BudgetPlanApi {
    private final CreateBudgetPlanUseCase createBudgetPlanUseCase;
    private final UpdateBudgetPlanUseCase updateBudgetPlanUseCase;
    private final FindBudgetPlanUseCase findBudgetPlanUseCase;
    private final FindFilteredBudgetPlansUseCase findFilteredBudgetPlansUseCase;
    private final BudgetDtoMapper budgetDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetPlanCreateResponse create(
            @RequestBody @Valid BudgetPlanCreateRequest request
    ) {
        var budgetPlan = createBudgetPlanUseCase.create(request);

        return budgetDtoMapper.toCreateResponse(budgetPlan);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetPlanUpdateResponse update(
            @PathVariable Long id,
            @RequestBody @Valid BudgetPlanUpdateRequest request
    ) {
        var updatedPlan = updateBudgetPlanUseCase.update(id, request);

        return budgetDtoMapper.toUpdateResponse(updatedPlan);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetPlanDetailResponse getBudgetPlanById(@PathVariable Long id) {
        return budgetDtoMapper.toDetailResponse(findBudgetPlanUseCase.findBudgetPlanById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BudgetPlanQueryDto.BudgetPlanSearchResponse getFilteredBudgetPlans(
            @PageableDefault(size = 5) Pageable pageable,
            @RequestParam(required = false) String executeType,
            @RequestParam(required = false) String clubName,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) LocalDateTime expectedPaymentDate,
            @RequestParam(required = false) Integer paymentAmount,
            @RequestParam(required = false) LocalDateTime createdAt,
            @RequestParam(required = false) BudgetStatus status
    ) {
        var budgetPlanPage = findFilteredBudgetPlansUseCase.findFilteredBudgetPlans(
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

        var budgetPlanResponses = budgetDtoMapper.toSummaryResponseList(budgetPlanPage.getContent());

        return BudgetPlanQueryDto.BudgetPlanSearchResponse.builder()
                .content(budgetPlanResponses)
                .pageNumber(budgetPlanPage.getNumber())
                .pageSize(budgetPlanPage.getSize())
                .totalElements(budgetPlanPage.getTotalElements())
                .totalPages(budgetPlanPage.getTotalPages())
                .last(budgetPlanPage.isLast())
                .build();
    }
}
