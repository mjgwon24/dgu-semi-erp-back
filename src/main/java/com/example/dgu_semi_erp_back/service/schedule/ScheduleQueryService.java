package com.example.dgu_semi_erp_back.service.schedule;

import com.example.dgu_semi_erp_back.dto.schedule.ScheduleQueryDto.ScheduleDetail;
import com.example.dgu_semi_erp_back.entity.schedule.Schedule;
import com.example.dgu_semi_erp_back.entity.schedule.ScheduleExcluded;
import com.example.dgu_semi_erp_back.entity.schedule.type.ScheduleRepeat;
import com.example.dgu_semi_erp_back.mapper.ScheduleDtoMapper;
import com.example.dgu_semi_erp_back.repository.schedule.ScheduleQueryRepository;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleFindByClubIdUseCase;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleFindByMonthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleQueryService implements ScheduleFindByClubIdUseCase, ScheduleFindByMonthUseCase {

    private final ScheduleQueryRepository scheduleQueryRepository;
    private final ScheduleDtoMapper mapper;

    @Override
    public List<ScheduleDetail> findByClubId(Long clubId) {
        List<Schedule> schedules = scheduleQueryRepository.findByClubId(clubId);
        List<Schedule> expanded = expandRepeats(schedules);

        return mapper.toScheduleDetailList(expanded);
    }

    @Override
    public List<ScheduleDetail> findByMonth(Long clubId, int year, int month) { //6월꺼
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1);

        List<Schedule> schedules = scheduleQueryRepository.findByClubId(clubId);
        List<Schedule> expanded = expandRepeats(schedules);

        List<Schedule> filtered = expanded.stream()
                .filter(s -> !s.getDate().isBefore(start) && s.getDate().isBefore(end))
                .toList();

        return mapper.toScheduleDetailList(filtered);
    }

    //5월31일 매월반복 8월
    //6월 31 731 831 요청 달로 추려서 마감 8월 31 6~8 사이 일정??
    // 6월 쿼리 > 전체 조회 > 복사 > 필터
    // 전체 조회 시 클럽아이디, 마감일이 쿼리 달 이후 && 시작일이 쿼리 달 전
    //반복x 마감일 null 증식을 프론트에서?
    // 처음 증식을 프론트에서 본체가 다른달에 이번달에 안나왔음
    //**서버에서 최대한 줄여서 쿼리하고 증식은 프론트에서
    private List<Schedule> expandRepeats(List<Schedule> schedules) {
        List<Schedule> expanded = new ArrayList<>();

        for (Schedule schedule : schedules) {
            // 반복이 아닌 경우 그대로 추가
            if (schedule.getRepeat() == null || schedule.getRepeatEnd() == null) {
                expanded.add(schedule);
                continue;
            }

            LocalDateTime current = schedule.getDate();
            LocalDateTime end = schedule.getRepeatEnd().atTime(LocalTime.MAX);

            // 제외된 날짜 리스트
            List<LocalDate> excluded = schedule.getExcludedDates().stream()
                    .map(ScheduleExcluded::getExcludedDate)
                    .toList();

            while (!current.isAfter(end)) {
                boolean isExcluded = excluded.contains(current.toLocalDate());

                if (!isExcluded) {
                    Schedule copy = Schedule.builder()
                            .id(schedule.getId())
                            .club(schedule.getClub())
                            .title(schedule.getTitle())
                            .date(current)
                            .place(schedule.getPlace())
                            .repeat(schedule.getRepeat())
                            .repeatEnd(schedule.getRepeatEnd())
                            .createdAt(schedule.getCreatedAt())
                            .build();

                    expanded.add(copy);
                }

                // 다음 반복일로 이동
                switch (schedule.getRepeat()) {
                    case DAILY -> current = current.plusDays(1);
                    case WEEKLY -> current = current.plusWeeks(1);
                    case MONTHLY -> current = current.plusMonths(1);
                }
            }
        }

        return expanded;
    }

}
