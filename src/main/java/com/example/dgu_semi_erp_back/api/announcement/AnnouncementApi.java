package com.example.dgu_semi_erp_back.api.announcement;

import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementCommandDto.AnnouncementCreateRequest;
import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementCommandDto.AnnouncementUpdateRequest;
import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementQueryDto.AnnouncementSummariesListResponse;
import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementCommandDto.AnnouncementCreateResponse;
import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementCommandDto.AnnouncementUpdateResponse;
import com.example.dgu_semi_erp_back.entity.announcement.Announcement;
import com.example.dgu_semi_erp_back.mapper.AnnouncementDtoMapper;
import com.example.dgu_semi_erp_back.usecase.announcement.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/announcement")
@Validated
public class AnnouncementApi {
    private final AnnouncementUseCase announcementUseCase;
    private final CreateAnnouncementUseCase createAnnouncementUseCase;
    private final UpdateAnnouncementUseCase updateAnnouncementUseCase;
    private final DeleteAnnouncementUsecase deleteAnnouncementUseCase;
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
            @PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC, size = 6) Pageable pageable, // 한 페이지에 조회되는 목록의 수
            // 파라미터 조회에서 비어있어도 무관함, front에서 date만 주는 관계로
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
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

    // 공지글 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnnouncementCreateResponse create(
            @RequestBody @Valid AnnouncementCreateRequest request
    ){
        var announcement = createAnnouncementUseCase.create(request);

        return announcementDtoMapper.toCreateResponse(announcement);
    }

    // 공지글 업데이트
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AnnouncementUpdateResponse update(
            @PathVariable Long id,
            @RequestBody @Valid AnnouncementUpdateRequest request
    ){
        var updatedAnnouncement = updateAnnouncementUseCase.update(id, request);

        return announcementDtoMapper.toUpdateResponse(updatedAnnouncement);
    }

    // 공지글 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteAnnouncement(@PathVariable Long id) {
        deleteAnnouncementUseCase.deleteAnnouncement(id);
        return ResponseEntity.ok("게시물이 삭제되었습니다.");
    }
}