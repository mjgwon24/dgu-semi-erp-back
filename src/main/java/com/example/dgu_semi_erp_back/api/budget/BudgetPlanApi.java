package com.example.dgu_semi_erp_back.api.budget;

import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanCreateRequest;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanCreateResponse;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanUpdateResponse;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanUpdateRequest;
import com.example.dgu_semi_erp_back.usecase.budget.BudgetPlanCreateUseCase;
import com.example.dgu_semi_erp_back.usecase.budget.BudgetPlanUpdateUseCase;
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
    private final BudgetPlanCreateUseCase budgetPlanCreateUseCase;
    private final BudgetPlanUpdateUseCase budgetPlanUpdateUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetPlanCreateResponse create(
            @RequestBody @Valid BudgetPlanCreateRequest request
    ) {
        var budgetPlan = budgetPlanCreateUseCase.create(request);

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
        var updatedPlan = budgetPlanUpdateUseCase.update(id, request);

        return BudgetPlanUpdateResponse.builder()
                .budgetPlan(updatedPlan)
                .build();
    }
}
