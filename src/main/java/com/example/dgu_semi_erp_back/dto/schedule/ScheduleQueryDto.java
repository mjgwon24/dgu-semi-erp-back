package com.example.dgu_semi_erp_back.dto.schedule;

import com.example.dgu_semi_erp_back.entity.schedule.type.ScheduleRepeat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleQueryDto {

    @Builder
    public record ScheduleDetail(
            Long id,
            String title,
            LocalDateTime date,
            String place,
            ScheduleRepeat repeat
    ) {}

    @Builder
    public record ScheduleListResponse(
            @JsonProperty("club_id")
            Long clubId,
            List<ScheduleDetail> scheduleList
    ) {}
}
