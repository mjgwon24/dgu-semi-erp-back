package com.example.dgu_semi_erp_back.service.peoplemanagement;

import com.example.dgu_semi_erp_back.dto.account.AccountHistoryCommandDto;
import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto;
import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.ClubMemberDetail;
import com.example.dgu_semi_erp_back.dto.common.PaginationInfo;
import com.example.dgu_semi_erp_back.entity.auth.user.QUser;
import com.example.dgu_semi_erp_back.entity.club.*;
import com.example.dgu_semi_erp_back.exception.ClubNotFoundException;
import com.example.dgu_semi_erp_back.projection.club.ClubMemberProjection;
import com.example.dgu_semi_erp_back.projection.club.ClubProjection;
import com.example.dgu_semi_erp_back.repository.club.ClubRepository;
import com.example.dgu_semi_erp_back.repository.peoplemanagement.UserQueryRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserClubMemberService {

    private final UserQueryRepository queryRepository;
    private final JPAQueryFactory queryFactory;
    private final ClubRepository clubRepository;

    public UserClubMemberDto.ClubMemberDetailSearchResponse getClubMembers(Long clubId, String status, Pageable pageable) {
        ClubProjection.ClubDetail club = clubRepository.findDetailById(clubId).orElseThrow(()-> new ClubNotFoundException("해당 동아리가 존재하지 않습니다."));
        Page<ClubMember> clubmemberPage = queryRepository.findClubMembersByClubIdAndStatus(clubId, MemberStatus.valueOf(status), pageable);
        QClub qClub = QClub.club;
        QClubMember qMember = QClubMember.clubMember;
        QUser qUser = QUser.user;

        List<ClubMemberDetail> clubMemberDetails = queryFactory
                .select(Projections.constructor(
                        ClubMemberDetail.class,
                        qUser.username,
                        qUser.major,
                        qUser.studentNumber,
                        qMember.role,
                        qMember.registeredAt,
                        qMember.status
                ))
                .from(qMember)
                .join(qUser).on(qMember.user.id.eq(qUser.id))
                .join(qClub).on(qMember.club.id.eq(qClub.id))
                .where(
                        qMember.club.id.eq(clubId)
                        .and(qMember.status.eq(MemberStatus.valueOf(status)))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return UserClubMemberDto.ClubMemberDetailSearchResponse.builder()
                .club(club)
                .content(clubMemberDetails)
                .paginationInfo(
                        new PaginationInfo(
                                clubmemberPage.getNumber(),
                                clubmemberPage.getSize(),
                                clubmemberPage.getTotalPages(),
                                clubmemberPage.getTotalElements()
                        )
                ).build();
    }
}