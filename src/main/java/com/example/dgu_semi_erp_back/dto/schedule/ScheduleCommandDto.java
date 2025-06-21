package com.example.dgu_semi_erp_back.dto.schedule;

import com.example.dgu_semi_erp_back.entity.schedule.Schedule;
import com.example.dgu_semi_erp_back.entity.schedule.type.ScheduleRepeat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ScheduleCommandDto {


    @Builder
    public record ScheduleCreateRequest(
            @JsonProperty("club_id")
            Long clubId,

            String title,
            LocalDateTime date,
            String place,
            ScheduleRepeat repeat,

            @JsonProperty("repeat_end")
            LocalDate repeatEnd
    ){}

    @Builder
    public record ScheduleCreateResponse(
            @JsonProperty("club_id")
            Long clubId,

            String title,
            LocalDateTime date,
            String place,
            ScheduleRepeat repeat,
            LocalDate repeatEnd
    ){}

    @Builder
    public record ScheduleUpdateRequest(
            String title,
            LocalDateTime date,
            String place,
            ScheduleRepeat repeat,
            @JsonProperty("repeat_end")
            LocalDate repeatEnd
    ){}

    @Builder
    public record ScheduleUpdateResponse(
            @JsonProperty("club_id")
            Long clubId,

            String title,
            LocalDateTime date,
            String place,
            ScheduleRepeat repeat,
            LocalDate repeatEnd
    ){}

    @Builder
    public record ScheduleExcludedRequest(
            @JsonProperty("schedule_id")
            Long scheduleId,

            @JsonProperty("excluded_date")
            LocalDate excludedDate
    ){}

    public record MessageResponse(String message) {}
}
