//package com.example.dgu_semi_erp_back.repository.peoplemanagement;
//
//import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.ClubMemberDetail;
//import com.example.dgu_semi_erp_back.entity.club.ClubStatus;
//import com.example.dgu_semi_erp_back.entity.club.QClub;
//import com.example.dgu_semi_erp_back.entity.club.QClubMember;
//import com.example.dgu_semi_erp_back.entity.auth.user.QUser;
//import com.example.dgu_semi_erp_back.mapper.UserClubMemberMapper;
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class UserQueryRepositoryImpl implements UserQueryRepository {
//
//    private final JPAQueryFactory queryFactory;
//    private final UserClubMemberMapper mapper;
//
//    @Override
//    public Page<ClubMemberDetail> findClubMembers(String clubName, ClubStatus status, Pageable pageable) {
//        QClub club = QClub.club;
//        QClubMember cm = QClubMember.clubMember;
//        QUser user = QUser.user;
//
//        BooleanBuilder builder = new BooleanBuilder();
//        if (clubName != null) builder.and(club.name.eq(clubName));
//        if (status != null) builder.and(club.status.eq(status));
//
//        List<ClubMemberDetail> content = queryFactory
//                .selectFrom(cm)
//                .join(cm.club, club).fetchJoin()
//                .join(cm.user, user).fetchJoin()
//                .where(builder)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch()
//                .stream()
//                .map(mapper::toDto)
//                .toList();
//
//        Long count = queryFactory
//                .select(cm.count())
//                .from(cm)
//                .join(cm.club, club)
//                .where(builder)
//                .fetchOne();
//
//        return new PageImpl<>(content, pageable, count != null ? count : 0);
//    }
//}