package com.example.dgu_semi_erp_back.mapper;

import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;
import com.example.dgu_semi_erp_back.entity.budget.BudgetUsage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BudgetUsageMapper {
    @Mapping(source = "budgetPlan.expectedPaymentDate", target = "paymentDate")
    @Mapping(source = "budgetPlan.planReviewer", target = "usageReviewer")
    @Mapping(source = "budgetPlan.planApprover", target = "usageApprover")
    @Mapping(target = "file", ignore = true)
    @Mapping(target = "id", ignore = true)
    BudgetUsage toEntity(
        BudgetPlan budgetPlan,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    );
}
