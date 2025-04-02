package com.example.dgu_semi_erp_back.api;

import com.example.dgu_semi_erp_back.dto.budget.BudgetCommandDto.BudgetPlanUpdateRequest;
import com.example.dgu_semi_erp_back.dto.budget.BudgetCommandDto.BudgetPlanUpdateResponse;
import com.example.dgu_semi_erp_back.dto.budget.BudgetCommandDto.BudgetPlanCreateRequest;
import com.example.dgu_semi_erp_back.dto.budget.BudgetCommandDto.BudgetPlanCreateResponse;
import com.example.dgu_semi_erp_back.usecase.budget.BudgetCreateUseCase;
import com.example.dgu_semi_erp_back.usecase.budget.BudgetUpdateUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/budget")
@Validated
public class BudgetApi {
    private final BudgetCreateUseCase budgetCreateUseCase;
    private final BudgetUpdateUseCase budgetUpdateUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetPlanCreateResponse create(
            @RequestBody @Valid BudgetPlanCreateRequest request
    ) {
        var budgetPlan = budgetCreateUseCase.create(request);

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
        var updatedPlan = budgetUpdateUseCase.update(id, request);

        return BudgetPlanUpdateResponse.builder()
                .budgetPlan(updatedPlan)
                .build();
    }
}
