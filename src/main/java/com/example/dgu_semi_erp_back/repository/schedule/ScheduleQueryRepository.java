package com.example.dgu_semi_erp_back.repository.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.dgu_semi_erp_back.entity.schedule.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleQueryRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByClubId(Long clubId);
    List<Schedule> findByClubIdAndDateBetween(Long clubId, LocalDateTime start, LocalDateTime end);
}
