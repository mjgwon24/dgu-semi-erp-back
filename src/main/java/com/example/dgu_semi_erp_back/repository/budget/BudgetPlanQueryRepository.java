package com.example.dgu_semi_erp_back.repository.budget;

import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;
import com.example.dgu_semi_erp_back.projection.budget.BudgetPlanProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetPlanQueryRepository extends JpaRepository<BudgetPlan, Long> {
    Optional<BudgetPlanProjection.BudgetPlanDetailProjection> findBudgetPlanById(Long id);
    Page<BudgetPlanProjection.BudgetPlanSummaryProjection> findAllBy(Pageable pageable);
}
