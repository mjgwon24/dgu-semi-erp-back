package com.example.dgu_semi_erp_back.repository.budget;

import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetPlanCommandRepository extends JpaRepository<BudgetPlan, Long> {
}
