package com.example.dgu_semi_erp_back.repository.schedule;
import com.example.dgu_semi_erp_back.entity.schedule.ScheduleExcluded;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleExcludedCommandRepository extends JpaRepository<ScheduleExcluded, Long> {
}
