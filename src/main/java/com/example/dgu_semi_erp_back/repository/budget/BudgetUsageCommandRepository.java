package com.example.dgu_semi_erp_back.repository.budget;

import com.example.dgu_semi_erp_back.entity.budget.BudgetUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetUsageCommandRepository extends JpaRepository<BudgetUsage, Long> {
}
