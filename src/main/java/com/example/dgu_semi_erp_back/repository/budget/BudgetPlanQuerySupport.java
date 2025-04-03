package com.example.dgu_semi_erp_back.repository.budget;

import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;
import com.example.dgu_semi_erp_back.entity.budget.QBudgetPlan;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class BudgetPlanQuerySupport extends QuerydslRepositorySupport {
    QBudgetPlan qBudget = QBudgetPlan.budgetPlan;

    public BudgetPlanQuerySupport() {
        super(BudgetPlan.class);
    }
}
