package com.example.dgu_semi_erp_back.api.schedule;

import com.example.dgu_semi_erp_back.dto.schedule.ScheduleCommandDto.*;
import com.example.dgu_semi_erp_back.dto.schedule.ScheduleQueryDto.*;
import com.example.dgu_semi_erp_back.exception.ClubNotFoundException;
import com.example.dgu_semi_erp_back.exception.ScheduleNotFoundException;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleCreateUseCase;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleFindByClubIdUseCase;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleFindByMonthUseCase;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleUpdateUseCase;
import com.example.dgu_semi_erp_back.usecase.schedule.ScheduleDeleteUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final ScheduleDeleteUseCase scheduleDeleteUseCase;
    private final ScheduleFindByClubIdUseCase scheduleFindByClubIdUseCase;
    private final ScheduleFindByMonthUseCase scheduleFindByMonthUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageResponse> create(
            @RequestBody @Valid ScheduleCreateRequest request
    ) {
        try {
            var schedule = scheduleCreateUseCase.create(request);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MessageResponse("일정이 추가되었습니다."));

        } catch (ClubNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid ScheduleUpdateRequest request
    ) {

        try{
            var updatedSchedule = scheduleUpdateUseCase.update(id, request);

            return ResponseEntity.ok(new MessageResponse("일정이 수정되었습니다."));


        } catch (ScheduleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (ClubNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{club_id}")
    @ResponseStatus(HttpStatus.OK)
    public ScheduleListResponse findByClubId(
            @PathVariable("club_id") Long clubId
    ) {
        try {
            var schedules = scheduleFindByClubIdUseCase.findByClubId(clubId);

            return ScheduleListResponse.builder()
                    .scheduleList(schedules)
                    .build();

        } catch (ScheduleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (ClubNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ScheduleListResponse findByMonth(
            @RequestParam("club_id") Long clubId,
            @RequestParam("year") int year,
            @RequestParam("month") int month
    ) {
        try {
            var schedules = scheduleFindByMonthUseCase.findByMonth(clubId, year, month);

            return ScheduleListResponse.builder()
                    .scheduleList(schedules)
                    .build();

        } catch (ScheduleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (ClubNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ScheduleDeleteResponse(
            @PathVariable Long id
    ) {
        try {
            scheduleDeleteUseCase.delete(id);
        } catch (ScheduleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}