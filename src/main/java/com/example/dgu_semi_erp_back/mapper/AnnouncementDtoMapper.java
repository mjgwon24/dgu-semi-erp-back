package com.example.dgu_semi_erp_back.mapper;

import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementQueryDto.AnnouncementSummariesListResponse;
import com.example.dgu_semi_erp_back.dto.announcement.AnnouncementQueryDto.AnnouncementSummariesListResponse.AnnouncementSummaryResponse;
import com.example.dgu_semi_erp_back.projection.announcement.AnnouncementProjection.AnnouncementSummary;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

// Mapper = DB에 조작을 가할 목적으로 사용
@Mapper(componentModel = SPRING)
public interface AnnouncementDtoMapper {
//    AnnouncementSummariesListResponse toSummaryResponse(AnnouncementSummary summary);
    List<AnnouncementSummaryResponse> toSummaryResponseList(List<AnnouncementSummary> summaries);
}
