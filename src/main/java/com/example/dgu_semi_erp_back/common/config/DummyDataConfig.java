package com.example.dgu_semi_erp_back.common.config;

import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.entity.club.MemberStatus;
import com.example.dgu_semi_erp_back.entity.schedule.Schedule;
import com.example.dgu_semi_erp_back.entity.schedule.type.ScheduleRepeat;
import com.example.dgu_semi_erp_back.repository.club.ClubRepository;
import com.example.dgu_semi_erp_back.repository.schedule.ScheduleQueryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDateTime;

@Configuration
public class DummyDataConfig {

    private final ClubRepository clubRepository;
    private final ScheduleQueryRepository scheduleQueryRepository;

    public DummyDataConfig(ClubRepository clubRepository, ScheduleQueryRepository scheduleQueryRepository) {
        this.clubRepository = clubRepository;
        this.scheduleQueryRepository = scheduleQueryRepository;
    }

    @Bean
    CommandLineRunner initClubData(ClubRepository clubRepository) {
        return args -> {
            Club club = Club.builder()
                    .name("디벨로퍼")
                    .affiliation("공대")
                    .status(MemberStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            clubRepository.save(club);
        };
    }

    @Bean
    CommandLineRunner initScheduleData(ScheduleQueryRepository scheduleQueryRepository) {
        return args -> {
            Schedule schedule = Schedule.builder()
                    .club(clubRepository.findById(1L).orElseThrow(() -> new RuntimeException("Club not found")))
                    .title("정기모임")
                    .date(LocalDateTime.of(2025, 2, 17, 10, 30))
                    .place("공대 101호")
                    .repeat(ScheduleRepeat.WEEKLY)
                    .createdAt(Instant.now())
                    .build();

            scheduleQueryRepository.save(schedule);
        };
    }

    @Bean
    CommandLineRunner initScheduleData2(ScheduleQueryRepository scheduleQueryRepository) {
        return args -> {
            Schedule schedule = Schedule.builder()
                    .club(clubRepository.findById(1L).orElseThrow(() -> new RuntimeException("Club not found")))
                    .title("정기모임2")
                    .date(LocalDateTime.of(2025, 3, 17, 10, 30))
                    .place("공대 101호")
                    .repeat(ScheduleRepeat.WEEKLY)
                    .createdAt(Instant.now())
                    .build();

            scheduleQueryRepository.save(schedule);
        };
    }

    @Bean
    CommandLineRunner initScheduleData3(ScheduleQueryRepository scheduleQueryRepository) {
        return args -> {
            Schedule schedule = Schedule.builder()
                    .club(clubRepository.findById(1L).orElseThrow(() -> new RuntimeException("Club not found")))
                    .title("정기모임3")
                    .date(LocalDateTime.of(2025, 4, 17, 10, 30))
                    .place("공대 101호")
                    .repeat(ScheduleRepeat.WEEKLY)
                    .createdAt(Instant.now())
                    .build();

            scheduleQueryRepository.save(schedule);
        };
    }

}