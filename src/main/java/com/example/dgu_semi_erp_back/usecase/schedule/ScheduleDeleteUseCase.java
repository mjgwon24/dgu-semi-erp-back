package com.example.dgu_semi_erp_back.usecase.schedule;


import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleDeleteUseCase {
    void delete(Long id);

    void addExcludedDates(Long scheduleId, LocalDate excludedDates);
}
