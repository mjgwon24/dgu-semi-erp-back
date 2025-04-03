package com.example.dgu_semi_erp_back.api.budget;

import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanCreateRequest;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanCreateResponse;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanUpdateResponse;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanUpdateRequest;
import com.example.dgu_semi_erp_back.projection.budget.BudgetPlanProjection.BudgetPlanDetail;
import com.example.dgu_semi_erp_back.usecase.budget.BudgetCreatePlanUseCase;
import com.example.dgu_semi_erp_back.usecase.budget.BudgetFindByIdUseCase;
import com.example.dgu_semi_erp_back.usecase.budget.BudgetUpdatePlanUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/budget/plan")
@Validated
public class BudgetPlanApi {
    private final BudgetCreatePlanUseCase budgetCreatePlanUseCase;
    private final BudgetUpdatePlanUseCase budgetUpdatePlanUseCase;
    private final BudgetFindByIdUseCase budgetFindByIdUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetPlanCreateResponse create(
            @RequestBody @Valid BudgetPlanCreateRequest request
    ) {
        var budgetPlan = budgetCreatePlanUseCase.create(request);

        return BudgetPlanCreateResponse.builder()
                .budgetPlan(budgetPlan)
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetPlanUpdateResponse update(
            @PathVariable Long id,
            @RequestBody @Valid BudgetPlanUpdateRequest request
    ) {
        var updatedPlan = budgetUpdatePlanUseCase.update(id, request);

        return BudgetPlanUpdateResponse.builder()
                .budgetPlan(updatedPlan)
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetPlanDetail findById(@PathVariable Long id) {
        return budgetFindByIdUseCase.findBudgetPlanById(id);
    }
}
