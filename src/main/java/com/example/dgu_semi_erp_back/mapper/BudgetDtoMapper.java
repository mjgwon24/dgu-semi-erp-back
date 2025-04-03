package com.example.dgu_semi_erp_back.mapper;

import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanCreateRequest;
import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;
import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BudgetDtoMapper {
    BudgetPlan toEntity(
            BudgetPlanCreateRequest dto,
            BudgetStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    );
}