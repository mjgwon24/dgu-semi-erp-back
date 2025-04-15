package com.example.dgu_semi_erp_back.service.schedule;

import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.entity.schedule.Schedule;
import com.example.dgu_semi_erp_back.exception.ClubNotFoundException;
import com.example.dgu_semi_erp_back.mapper.ScheduleDtoMapper;
import com.example.dgu_semi_erp_back.repository.club.ClubRepository;
import com.example.dgu_semi_erp_back.repository.schedule.ScheduleQueryRepository;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleCreateUseCase;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleUpdateUseCase;
import com.example.dgu_semi_erp_back.dto.schedule.ScheduleCommandDto.*;
import lombok.Locked;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleCommandService implements ScheduleCreateUseCase {
    private final ScheduleQueryRepository scheduleQueryRepository;
    private final ScheduleDtoMapper mapper;
    private final ClubRepository clubRepository;

    @Override
    public Schedule create(ScheduleCreateRequest request) {
        Instant now = Instant.now();

        Club club = clubRepository.findById(request.clubId())
                .orElseThrow(() -> new ClubNotFoundException("해당 동아리가 존재하지 않습니다."));

        Schedule schedule = mapper.toEntity(request, club, now);
        return scheduleQueryRepository.save(schedule);
    }
}
