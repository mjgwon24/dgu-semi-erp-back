package com.example.dgu_semi_erp_back.mapper;

import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanUpdateResponse;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanCreateResponse;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanCreateRequest;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanQueryDto.BudgetPlanSearchResponse.BudgetPlanSummaryResponse;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanQueryDto.BudgetPlanDetailResponse;
import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;
import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.projection.budget.BudgetPlanProjection.BudgetPlanSummary;
import com.example.dgu_semi_erp_back.projection.budget.BudgetPlanProjection.BudgetPlanDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BudgetDtoMapper {
    @Mapping(target = "club", source = "club")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "id", ignore = true)
    BudgetPlan toEntity(
            BudgetPlanCreateRequest dto,
            Club club,
            BudgetStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    );

    @Mapping(target = "clubName", source = "club.name")
    BudgetPlanCreateResponse toCreateResponse(BudgetPlan budgetPlan);

    @Mapping(target = "clubName", source = "club.name")
    BudgetPlanUpdateResponse toUpdateResponse(BudgetPlan budgetPlan);

    @Mapping(target = "clubName", source = "club.name")
    BudgetPlanDetailResponse toDetailResponse(BudgetPlanDetail budgetPlanDetail);

    @Mapping(target = "clubName", source = "club.name")
    BudgetPlanSummaryResponse toSummaryResponse(BudgetPlanSummary summary);

    List<BudgetPlanSummaryResponse> toSummaryResponseList(List<BudgetPlanSummary> summaries);
}