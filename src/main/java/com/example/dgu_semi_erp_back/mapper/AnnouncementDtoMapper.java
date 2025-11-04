package com.example.dgu_semi_erp_back.mapper;

import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementCommandDto;
import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementCommandDto.AnnouncementCreateResponse;
import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementCommandDto.AnnouncementUpdateResponse;
import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementQueryDto.AnnouncementSummariesListResponse.AnnouncementSummaryResponse;
import com.example.dgu_semi_erp_back.entity.announcement.Announcement;
import com.example.dgu_semi_erp_back.projection.announcement.AnnouncementProjection.AnnouncementSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

// Mapper = DB에 조작을 가할 목적으로 사용
@Mapper(componentModel = SPRING)
public interface AnnouncementDtoMapper {
    @Mapping(target = "id", ignore = true)
    Announcement toEntity(
            AnnouncementCommandDto.AnnouncementCreateRequest dto,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    );
// 공지사항 요약 보기용
    List<AnnouncementSummaryResponse> toSummaryResponseList(List<AnnouncementSummary> summaries);

    AnnouncementCreateResponse toCreateResponse(Announcement announcement);

    AnnouncementUpdateResponse toUpdateResponse(Announcement announcement);
}