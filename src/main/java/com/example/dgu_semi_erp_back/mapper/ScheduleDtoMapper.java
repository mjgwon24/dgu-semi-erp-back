package com.example.dgu_semi_erp_back.mapper;

import com.example.dgu_semi_erp_back.dto.schedule.ScheduleCommandDto.*;
import com.example.dgu_semi_erp_back.dto.schedule.ScheduleQueryDto.*;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.entity.schedule.Schedule;
import com.example.dgu_semi_erp_back.entity.schedule.type.ScheduleRepeat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.Instant;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ScheduleDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", source = "createdAt")
    Schedule toEntity(
            ScheduleCreateRequest dto,
            Club club,
            Instant createdAt
    );

    List<ScheduleDetail> toScheduleDetailList(List<Schedule> schedules);

}
