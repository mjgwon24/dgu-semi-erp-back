package com.example.dgu_semi_erp_back.projection.club;

import com.example.dgu_semi_erp_back.entity.club.ClubMember;
import com.example.dgu_semi_erp_back.entity.club.ClubStatus;
import com.example.dgu_semi_erp_back.entity.club.MemberStatus;
import com.example.dgu_semi_erp_back.entity.club.Role;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClubProjection {
    public record ClubSummary(
            Long id,
            String name,
            String affiliation,
            ClubStatus status,
            List<ClubMemberProjection.ClubMemberSummery> clubMembers
    ) {
        @QueryProjection
        public ClubSummary(Long id, String name, String affiliation, ClubStatus status, List<ClubMemberProjection.ClubMemberSummery> clubMembers) {
            this.id = id;
            this.name = name;
            this.affiliation = affiliation;
            this.status = status;
            this.clubMembers = clubMembers;
        }
    }
    public record ClubDetail(
            Long id,
            String name,
            String affiliation,
            ClubStatus status
    ) {}
}
