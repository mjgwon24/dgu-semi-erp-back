package com.example.dgu_semi_erp_back.mapper;

import com.example.dgu_semi_erp_back.dto.budgetplan.BudgetPlanCreateRequest;
import com.example.dgu_semi_erp_back.dto.budgetplan.BudgetPlanUpdateRequest;
import com.example.dgu_semi_erp_back.dto.budgetplan.BudgetPlanReadResponse;
import com.example.dgu_semi_erp_back.entity.Budget.BudgetPlan;
import com.example.dgu_semi_erp_back.entity.Budget.BudgetStatus;
import com.example.dgu_semi_erp_back.entity.Budget.PaymentType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BudgetPlanDtoMapper {
    // Create: DTO → Entity
    @Mapping(target = "id", ignore = true)
    BudgetPlan toEntity(BudgetPlanCreateRequest dto, BudgetStatus status, LocalDateTime createdAt);

    // Read: Entity → DTO
    @Mapping(target = "paymentDate", expression = "java(plan.getPaymentDate().toString())")
    @Mapping(target = "createdAt", expression = "java(plan.getCreatedAt().toString())")
    BudgetPlanReadResponse toReadResponse(BudgetPlan plan);

    // Update: Entity 복사 후 수정
    default BudgetPlan updateEntity(BudgetPlan plan, BudgetPlanUpdateRequest dto) {
        return BudgetPlan.builder()
                .id(plan.getId())
                .executeType(dto.executeType())
                .paymentType(PaymentType.valueOf(dto.paymentType()))
                .paymentDate(dto.paymentDate())
                .content(dto.content())
                .author(dto.author())
                .paymentAmount(dto.paymentAmount())
                .planReviewer(dto.planReviewer())
                .planApprover(dto.planApprover())
                .status(plan.getStatus())
                .createdAt(plan.getCreatedAt())
                .build();
    }
}