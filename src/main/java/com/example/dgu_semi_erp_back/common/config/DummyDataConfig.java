package com.example.dgu_semi_erp_back.common.config;

import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.entity.club.MemberStatus;
import com.example.dgu_semi_erp_back.entity.schedule.Schedule;
import com.example.dgu_semi_erp_back.entity.schedule.type.ScheduleRepeat;
import com.example.dgu_semi_erp_back.repository.club.ClubRepository;
import com.example.dgu_semi_erp_back.repository.schedule.ScheduleCommandRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DummyDataConfig {

    private final ClubRepository clubRepository;
    private final ScheduleCommandRepository scheduleCommandRepository;

    public DummyDataConfig(ClubRepository clubRepository, ScheduleCommandRepository scheduleCommandRepository) {
        this.clubRepository = clubRepository;
        this.scheduleCommandRepository = scheduleCommandRepository;
    }

    @Bean
    CommandLineRunner initData() {
        return args -> {
            Club club = Club.builder()
                    .name("디벨로퍼")
                    .affiliation("공대")
                    .status(MemberStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Club savedClub = clubRepository.save(club);

            clubRepository.save(
                    Club.builder()
                            .name("어쩌고 동아리")
                            .affiliation("어쩌고대")
                            .status(MemberStatus.ACTIVE)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );

            clubRepository.save(
                    Club.builder()
                            .name("머시기 동아리")
                            .affiliation("머시기대")
                            .status(MemberStatus.ACTIVE)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );

            LocalDate repeatEnd = LocalDate.now().plusMonths(3); // 3개월 뒤

            scheduleCommandRepository.save(
                    Schedule.builder()
                            .club(savedClub)
                            .title("매주 반복")
                            .date(LocalDateTime.of(2025, 2, 17, 10, 30))
                            .place("공대 101호")
                            .repeat(ScheduleRepeat.WEEKLY)
                            .repeatEnd(repeatEnd)
                            .createdAt(Instant.now())
                            .build()
            );

            scheduleCommandRepository.save(
                    Schedule.builder()
                            .club(savedClub)
                            .title("매월 반복")
                            .date(LocalDateTime.of(2025, 3, 17, 10, 30))
                            .place("공대 101호")
                            .repeat(ScheduleRepeat.MONTHLY)
                            .repeatEnd(repeatEnd)
                            .createdAt(Instant.now())
                            .build()
            );

            scheduleCommandRepository.save(
                    Schedule.builder()
                            .club(savedClub)
                            .title("매일 반복")
                            .date(LocalDateTime.of(2025, 4, 17, 10, 30))
                            .place("공대 101호")
                            .repeat(ScheduleRepeat.DAILY)
                            .repeatEnd(repeatEnd)
                            .createdAt(Instant.now())
                            .build()
            );
        };
    }
}