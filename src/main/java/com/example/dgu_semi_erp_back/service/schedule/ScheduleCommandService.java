package com.example.dgu_semi_erp_back.service.schedule;

import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.entity.schedule.Schedule;
import com.example.dgu_semi_erp_back.entity.schedule.ScheduleExcluded;
import com.example.dgu_semi_erp_back.exception.ClubNotFoundException;
import com.example.dgu_semi_erp_back.exception.ScheduleNotFoundException;
import com.example.dgu_semi_erp_back.mapper.ScheduleDtoMapper;
import com.example.dgu_semi_erp_back.mapper.ScheduleExcludedDtoMapper;
import com.example.dgu_semi_erp_back.repository.club.ClubRepository;
import com.example.dgu_semi_erp_back.repository.schedule.ScheduleCommandRepository;
import com.example.dgu_semi_erp_back.repository.schedule.ScheduleExcludedCommandRepository;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleCreateUseCase;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleDeleteUseCase;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleUpdateUseCase;
import com.example.dgu_semi_erp_back.dto.schedule.ScheduleCommandDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ScheduleCommandService implements ScheduleCreateUseCase, ScheduleUpdateUseCase, ScheduleDeleteUseCase {
    private final ScheduleCommandRepository scheduleCommandRepository;
    private final ScheduleExcludedCommandRepository scheduleExcludedCommandRepository;
    private final ScheduleDtoMapper mapper;
    private final ScheduleExcludedDtoMapper excludedMapper;
    private final ClubRepository clubRepository;

    @Override
    public Schedule create(ScheduleCreateRequest request) {
        Instant now = Instant.now();

        Club club = clubRepository.findById(request.clubId())
                .orElseThrow(() -> new ClubNotFoundException("해당 동아리가 존재하지 않습니다."));

        Schedule schedule = mapper.toEntity(request, club, now);
        return scheduleCommandRepository.save(schedule);
    }

    @Transactional
    @Override
    public Schedule update(Long id, ScheduleUpdateRequest request) {
        Schedule schedule = scheduleCommandRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("해당 일정이 존재하지 않습니다."));

        schedule.update(request.title(), request.date(), request.place(), request.repeat(), request.repeatEnd());

        return schedule;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Schedule schedule = scheduleCommandRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("해당 일정이 존재하지 않습니다."));

        scheduleCommandRepository.delete(schedule);
    }

    @Transactional
    @Override
    public void addExcludedDates(Long scheduleId, LocalDate excludedDate) {
        Schedule schedule = scheduleCommandRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("해당 일정이 존재하지 않습니다."));

        ScheduleExcluded excluded = excludedMapper.toEntity(excludedDate, schedule);


        scheduleExcludedCommandRepository.save(excluded);
    }


}
