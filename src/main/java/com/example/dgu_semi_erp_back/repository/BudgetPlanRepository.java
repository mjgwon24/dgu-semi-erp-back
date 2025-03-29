package com.example.dgu_semi_erp_back.repository;

import com.example.dgu_semi_erp_back.entity.Budget.BudgetPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetPlanRepository extends JpaRepository<BudgetPlan, Long> {
}