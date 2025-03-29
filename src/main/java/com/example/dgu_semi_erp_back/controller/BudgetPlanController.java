package com.example.dgu_semi_erp_back.controller;

import com.example.dgu_semi_erp_back.dto.budgetplan.*;
import com.example.dgu_semi_erp_back.service.BudgetPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/budget-plans")
public class BudgetPlanController {

    private final BudgetPlanService budgetPlanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody BudgetPlanCreateRequest request) {
        budgetPlanService.createBudgetPlan(request);
    }

    @GetMapping("/{id}")
    public BudgetPlanReadResponse get(@PathVariable Long id) {
        return budgetPlanService.getBudgetPlan(id);
    }

    @GetMapping
    public List<BudgetPlanReadResponse> getAll(@ModelAttribute BudgetPlanSearchCondition condition) {
        return budgetPlanService.searchBudgetPlans(condition);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody BudgetPlanUpdateRequest request) {
        budgetPlanService.updateBudgetPlan(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        budgetPlanService.softDeleteBudgetPlan(id);
    }
}
