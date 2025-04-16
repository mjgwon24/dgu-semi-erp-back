package com.example.dgu_semi_erp_back.api.schedule;

import com.example.dgu_semi_erp_back.dto.schedule.ScheduleCommandDto.*;
import com.example.dgu_semi_erp_back.exception.ClubNotFoundException;
import com.example.dgu_semi_erp_back.exception.ScheduleNotFoundException;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleCreateUseCase;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleUpdateUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
@Validated

public class ScheduleApi {
    private final ScheduleCreateUseCase scheduleCreateUseCase;
    private final ScheduleUpdateUseCase scheduleUpdateUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleCreateResponse create(
            @RequestBody @Valid ScheduleCreateRequest request
    ) {
        try {
            var schedule = scheduleCreateUseCase.create(request);

            return ScheduleCreateResponse.builder()
                    .clubId(schedule.getClub().getId())
                    .title(schedule.getTitle())
                    .date(schedule.getDate())
                    .place(schedule.getPlace())
                    .repeat(schedule.getRepeat())
                    .build();

        } catch (ClubNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ScheduleUpdateResponse update(
            @PathVariable Long id,
            @RequestBody @Valid ScheduleUpdateRequest request
    ) {

        try{
            var updatedSchedule = scheduleUpdateUseCase.update(id, request);

            return ScheduleUpdateResponse.builder()
                    .clubId(updatedSchedule.getClub().getId())
                    .title(updatedSchedule.getTitle())
                    .date(updatedSchedule.getDate())
                    .place(updatedSchedule.getPlace())
                    .repeat(updatedSchedule.getRepeat())
                    .build();


        } catch (ScheduleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (ClubNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}