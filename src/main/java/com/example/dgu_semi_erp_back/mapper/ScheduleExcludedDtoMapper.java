package com.example.dgu_semi_erp_back.mapper;

import com.example.dgu_semi_erp_back.entity.schedule.Schedule;
import com.example.dgu_semi_erp_back.entity.schedule.ScheduleExcluded;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ScheduleExcludedDtoMapper {
    @Mapping(target = "id", ignore = true)
    ScheduleExcluded toEntity(LocalDate excludedDate, Schedule schedule);
}
