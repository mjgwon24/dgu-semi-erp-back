package com.example.dgu_semi_erp_back.service.schedule;

import com.example.dgu_semi_erp_back.dto.schedule.ScheduleQueryDto.*;
import com.example.dgu_semi_erp_back.entity.schedule.Schedule;
import com.example.dgu_semi_erp_back.mapper.ScheduleDtoMapper;
import com.example.dgu_semi_erp_back.repository.schedule.ScheduleQueryRepository;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleFindByClubIdUseCase;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleFindByMonthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class ScheduleQueryService implements ScheduleFindByClubIdUseCase, ScheduleFindByMonthUseCase {
    private final ScheduleQueryRepository scheduleQueryRepository;
    private final ScheduleDtoMapper mapper;

    @Override
    public List<ScheduleDetail> findByClubId(Long clubId) {
        List<Schedule> schedules = scheduleQueryRepository.findByClubId(clubId);

        return mapper.toScheduleDetailList(schedules);
    }

    @Override
    public List<ScheduleDetail> findByMonth(Long clubId, int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1);

        List<Schedule> schedules = scheduleQueryRepository.findByClubIdAndDateBetween(clubId, start, end);


        return mapper.toScheduleDetailList(schedules.stream().toList());

    }


}
