package com.example.dgu_semi_erp_back.api.announcement;

import com.example.dgu_semi_erp_back.entity.announcement.Announcement;
import com.example.dgu_semi_erp_back.usecase.announcement.AnnouncementUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/announcement")
@Validated
public class AnnouncementApi {
    private final AnnouncementUseCase announcementUseCase;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Announcement findById(@PathVariable Long id) {
        return announcementUseCase.findAnnouncementById(id);
    }
}