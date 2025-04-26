package com.example.dgu_semi_erp_back.entity.schedule.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScheduleRepeat {
    DAILY("매일"),
    WEEKLY("매주"),
    MONTHLY("매월");

    private final String description;
}
