package com.example.dgu_semi_erp_back.usecase.schedule;

import com.example.dgu_semi_erp_back.dto.schedule.ScheduleCommandDto.ScheduleUpdateRequest;
import com.example.dgu_semi_erp_back.entity.schedule.Schedule;

public interface ScheduleUpdateUseCase {
    Schedule update(ScheduleUpdateRequest request);
}
