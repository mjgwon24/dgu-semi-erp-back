package com.example.dgu_semi_erp_back.dto.schedule;

import com.example.dgu_semi_erp_back.entity.schedule.Schedule;
import com.example.dgu_semi_erp_back.entity.schedule.type.ScheduleRepeat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;

public class ScheduleCommandDto {


    @Builder
    public record ScheduleCreateRequest(
            @JsonProperty("club_id")
            Long clubId,

            String title,
            LocalDateTime date,
            String place,
            ScheduleRepeat repeat
    ){}

    @Builder
    public record ScheduleCreateResponse(
            @JsonProperty("club_id")
            Long clubId,

            String title,
            LocalDateTime date,
            String place,
            ScheduleRepeat repeat
    ){}

    @Builder
    public record ScheduleUpdateRequest(
            Long id,
            String title,
            LocalDateTime date,
            String place,
            ScheduleRepeat repeat
    ){}

    @Builder
    public record ScheduleUpdateResponse(
            Schedule schedule
    ){}
}
