package com.example.dgu_semi_erp_back.usecase.schedule;

import com.example.dgu_semi_erp_back.dto.schedule.ScheduleCommandDto.ScheduleCreateRequest;
import com.example.dgu_semi_erp_back.entity.schedule.Schedule;


public interface ScheduleCreateUseCase {
    Schedule create(ScheduleCreateRequest request);
}
