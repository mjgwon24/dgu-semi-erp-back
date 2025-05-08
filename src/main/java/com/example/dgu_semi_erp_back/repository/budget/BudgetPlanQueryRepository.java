package com.example.dgu_semi_erp_back.repository.budget;

import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;
import com.example.dgu_semi_erp_back.projection.budget.BudgetPlanProjection.BudgetPlanSummary;
import com.example.dgu_semi_erp_back.projection.budget.BudgetPlanProjection.BudgetPlanDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetPlanQueryRepository extends JpaRepository<BudgetPlan, Long> {
    Optional<BudgetPlanDetail> findBudgetPlanById(Long id);
    Page<BudgetPlanSummary> findAllBy(Pageable pageable);
}
