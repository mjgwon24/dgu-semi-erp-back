package com.example.dgu_semi_erp_back.repository.budget;

import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;
import com.example.dgu_semi_erp_back.entity.budget.QBudgetPlan;
import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.projection.budget.BudgetPlanProjection.BudgetPlanSummary;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class BudgetPlanRepositorySupport extends QuerydslRepositorySupport {
    QBudgetPlan budgetPlan = QBudgetPlan.budgetPlan;

    public BudgetPlanRepositorySupport() {
        super(BudgetPlan.class);
    }

    public Page<BudgetPlanSummary> findFilteredBudgetPlans(
            Pageable pageable,
            String executeType,
            String clubName,
            String content,
            String author,
            LocalDateTime expectedPaymentDate,
            Integer paymentAmount,
            LocalDateTime createdAt,
            BudgetStatus status
    ) {
        BooleanExpression conditions = createFilterConditions(
                executeType, clubName, content, author,
                expectedPaymentDate, paymentAmount, createdAt, status
        );

        var query = getQuerydsl().createQuery()
                .select(Projections.constructor(
                        BudgetPlanSummary.class,
                        budgetPlan.id,
                        budgetPlan.executeType,
                        budgetPlan.club,
                        budgetPlan.content,
                        budgetPlan.author,
                        budgetPlan.expectedPaymentDate,
                        budgetPlan.paymentAmount,
                        budgetPlan.createdAt,
                        budgetPlan.status
                ))
                .from(budgetPlan)
                .leftJoin(budgetPlan.club)
                .where(conditions)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // Sorting 적용
        pageable.getSort().forEach(order -> {
            if (order.isAscending()) {
                query.orderBy(budgetPlan.createdAt.asc());
            } else {
                query.orderBy(budgetPlan.createdAt.desc());
            }
        });

        var result = query.fetch();

        // Total count 쿼리
        Long total = getQuerydsl().createQuery()
                .select(budgetPlan.count())
                .from(budgetPlan)
                .where(conditions)
                .fetchOne();

        return new PageImpl<>(result, pageable, total != null ? total : 0L);
    }

    private BooleanExpression createFilterConditions(
            String executeType,
            String clubName,
            String content,
            String author,
            LocalDateTime expectedPaymentDate,
            Integer paymentAmount,
            LocalDateTime createdAt,
            BudgetStatus status
    ) {
        BooleanExpression conditions = budgetPlan.isNotNull();

        if (executeType != null && !executeType.isEmpty()) {
            conditions = conditions.and(budgetPlan.executeType.eq(executeType));
        }
        if (clubName != null && !clubName.isEmpty()) {
            conditions = conditions.and(budgetPlan.club.name.like("%" + clubName + "%"));
        }
        if (content != null && !content.isEmpty()) {
            conditions = conditions.and(budgetPlan.content.like("%" + content + "%"));
        }
        if (author != null && !author.isEmpty()) {
            conditions = conditions.and(budgetPlan.author.like("%" + author + "%"));
        }
        if (expectedPaymentDate != null) {
            conditions = conditions.and(budgetPlan.expectedPaymentDate.eq(expectedPaymentDate));
        }
        if (paymentAmount != null) {
            conditions = conditions.and(budgetPlan.paymentAmount.eq(paymentAmount));
        }
        if (createdAt != null) {
            conditions = conditions.and(budgetPlan.createdAt.eq(createdAt));
        }
        if (status != null) {
            conditions = conditions.and(budgetPlan.status.eq(status));
        }

        return conditions;
    }
}