package com.example.dgu_semi_erp_back.api.announcement;

import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementQueryDto.AnnouncementSummariesListResponse;
import com.example.dgu_semi_erp_back.entity.announcement.Announcement;
import com.example.dgu_semi_erp_back.mapper.AnnouncementDtoMapper;
import com.example.dgu_semi_erp_back.service.announcement.AnnouncementQueryService;
import com.example.dgu_semi_erp_back.usecase.announcement.AnnouncementUseCase;
import com.example.dgu_semi_erp_back.usecase.announcement.FindAnnouncementSummariesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/announcement")
@Validated
public class AnnouncementApi {
    private final AnnouncementUseCase announcementUseCase;
    private final FindAnnouncementSummariesUseCase findAnnouncementSummariesUseCase;
    private final AnnouncementDtoMapper announcementDtoMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Announcement findById(@PathVariable Long id) {
        return announcementUseCase.findAnnouncementById(id);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AnnouncementSummariesListResponse getAnnouncementSummaries(
            @PageableDefault(size = 5) Pageable pageable,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) LocalDateTime createdAt,
            @RequestParam(required = false) LocalDateTime updatedAt
    ) {
        var announcementPage = findAnnouncementSummariesUseCase.findAnnouncementSummaries(
                pageable,
                title,
                content,
                createdAt,
                updatedAt
        );
        var announcementResponses = announcementDtoMapper.toSummaryResponseList(announcementPage.getContent());

        return AnnouncementSummariesListResponse.builder()
                .content(announcementResponses)
                .pageNumber(announcementPage.getNumber())
                .pageSize(announcementPage.getSize())
                .totalElements(announcementPage.getTotalElements())
                .totalPages(announcementPage.getTotalPages())
                .last(announcementPage.isLast())
                .build();
    }
}