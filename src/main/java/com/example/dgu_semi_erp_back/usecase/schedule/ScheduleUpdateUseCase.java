package com.example.dgu_semi_erp_back.usecase.schedule;

import com.example.dgu_semi_erp_back.dto.schedule.ScheduleCommandDto.ScheduleUpdateRequest;
import com.example.dgu_semi_erp_back.entity.schedule.Schedule;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ScheduleUpdateUseCase {
    Schedule update(Long id, ScheduleUpdateRequest request);
}
