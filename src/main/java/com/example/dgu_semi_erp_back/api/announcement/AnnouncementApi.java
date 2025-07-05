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
    // 상세 조회
    public Announcement findById(@PathVariable Long id) {
        return announcementUseCase.findAnnouncementById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    // 목록 조회
    public AnnouncementSummariesListResponse getAnnouncementSummaries(
            @PageableDefault(size = 5) Pageable pageable, // 한 페이지에 조회되는 목록의 수
            // 파라미터 조회에서 비어있어도 무관함.
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate
    ) {
        var announcementPage = findAnnouncementSummariesUseCase.findAnnouncementSummaries(
                pageable,
                startDate,
                endDate
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