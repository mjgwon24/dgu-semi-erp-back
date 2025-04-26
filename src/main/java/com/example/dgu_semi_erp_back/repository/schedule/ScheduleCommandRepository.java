package com.example.dgu_semi_erp_back.repository.schedule;

import com.example.dgu_semi_erp_back.entity.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleCommandRepository extends JpaRepository<Schedule, Long> {

}
