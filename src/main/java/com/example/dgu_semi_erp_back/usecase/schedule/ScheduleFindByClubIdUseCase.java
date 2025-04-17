package com.example.dgu_semi_erp_back.usecase.schedule;

import com.example.dgu_semi_erp_back.dto.schedule.ScheduleQueryDto.ScheduleDetail;

import java.util.List;

public interface ScheduleFindByClubIdUseCase {
    List<ScheduleDetail> findByClubId(Long clubId);
}
