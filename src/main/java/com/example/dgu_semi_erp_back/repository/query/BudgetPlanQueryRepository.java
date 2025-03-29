package com.example.dgu_semi_erp_back.repository.query;

import com.example.dgu_semi_erp_back.dto.budgetplan.BudgetPlanSearchCondition;
import com.example.dgu_semi_erp_back.entity.Budget.BudgetPlan;
import com.example.dgu_semi_erp_back.entity.Budget.QBudgetPlan;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BudgetPlanQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<BudgetPlan> search(BudgetPlanSearchCondition condition) {
        QBudgetPlan budgetPlan = QBudgetPlan.budgetPlan;

        BooleanBuilder builder = new BooleanBuilder();

        if (condition.executeType() != null) {
            builder.and(budgetPlan.executeType.eq(condition.executeType()));
        }
        if (condition.content() != null) {
            builder.and(budgetPlan.content.contains(condition.content()));
        }
        if (condition.status() != null) {
            builder.and(budgetPlan.status.stringValue().eq(condition.status()));
        }
        if (condition.author() != null) {
            builder.and(budgetPlan.author.eq(condition.author()));
        }
        if (condition.startDate() != null && condition.endDate() != null) {
            builder.and(budgetPlan.paymentDate.between(condition.startDate().atStartOfDay(), condition.endDate().atTime(23, 59)));
        }
        if (condition.minAmount() != null) {
            builder.and(budgetPlan.paymentAmount.goe(condition.minAmount()));
        }
        if (condition.maxAmount() != null) {
            builder.and(budgetPlan.paymentAmount.loe(condition.maxAmount()));
        }

        builder.and(budgetPlan.status.ne(com.example.dgu_semi_erp_back.entity.Budget.BudgetStatus.DELETED));

        return queryFactory
                .selectFrom(budgetPlan)
                .where(builder)
                .fetch();
    }
}
