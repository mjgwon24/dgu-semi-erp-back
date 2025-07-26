package com.example.dgu_semi_erp_back.repository.club;

import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.ClubAccountFilter;
import com.example.dgu_semi_erp_back.entity.account.QAccount;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.entity.club.MemberStatus;
import com.example.dgu_semi_erp_back.entity.club.QClub;
import com.example.dgu_semi_erp_back.entity.club.QClubMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ClubQueryRepositoryImpl implements ClubQueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 통장을 보유한 동아리 목록 조회
     * - account 정보를 보유한 동아리 목록을 페이지 단위로 조회
     */
    @Override
    public Page<Club> getPagedAccounts(Pageable pageable) {
        QClub club = QClub.club;
        QAccount account = QAccount.account;
        Instant now = Instant.now();

        List<Club> clubs = queryFactory
                .selectFrom(club)
                .where(
                        queryFactory
                                .selectOne()
                                .from(account)
                                .where(
                                        account.club.eq(club),
                                        account.deletedAt.isNull()
                                                .or(account.deletedAt.gt(now))
                                )
                                .exists()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(club)
                .where(
                        queryFactory
                                .selectOne()
                                .from(account)
                                .where(
                                        account.club.eq(club),
                                        account.deletedAt.isNull()
                                                .or(account.deletedAt.gt(now))
                                )
                                .exists()
                )
                .fetchCount();

        return new PageImpl<>(clubs, pageable, total);
    }

    /**
     * 필터링 조건을 적용하여 통장을 보유한 동아리 목록 조회
     * - 상태(ACTIVE, INACTIVE, SUSPENDED)
     * - 동아리명(부분 일치)
     * - 활동 인원 범위(최소, 최대)
     * - 누적 인원 범위(최소, 최대)
     */
    @Override
    public Page<Club> getPagedAccountsWithFilter(Pageable pageable, ClubAccountFilter filter) {
        QClub club = QClub.club;
        QAccount account = QAccount.account;
        QClubMember clubMember = QClubMember.clubMember;
        Instant now = Instant.now();

        // 통장을 보유한 동아리 조회
        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(
                JPAExpressions
                        .selectOne()
                        .from(account)
                        .where(
                                account.club.eq(club),
                                account.deletedAt.isNull()
                                        .or(account.deletedAt.gt(now))
                        )
                        .exists()
        );

        // 동아리 상태
        if (filter.status() != null) {
            whereClause.and(club.status.eq(filter.status()));
        }

        // 동아리명 (부분 일치)
        if (StringUtils.hasText(filter.clubName())) {
            whereClause.and(club.name.containsIgnoreCase(filter.clubName()));
        }

        // 활동 인원 및 누적 인원 필터를 위한 서브쿼리
        JPAQuery<Club> query = queryFactory.selectFrom(club).where(whereClause);

        // 활동 인원 범위
        if (filter.minActiveMembers() != null || filter.maxActiveMembers() != null) {
            BooleanExpression activeMembersCondition = club.id.eq(clubMember.club.id)
                    .and(clubMember.status.eq(MemberStatus.ACTIVE));

            Long minActive = filter.minActiveMembers() != null ? filter.minActiveMembers().longValue() : null;
            Long maxActive = filter.maxActiveMembers() != null ? filter.maxActiveMembers().longValue() : null;

            applyMemberCountFilter(query, club, clubMember, activeMembersCondition, minActive, maxActive);
        }

        // 누적 인원 범위
        if (filter.minTotalMembers() != null || filter.maxTotalMembers() != null) {
            BooleanExpression totalMembersCondition = club.id.eq(clubMember.club.id);

            Long minTotal = filter.minTotalMembers() != null ? filter.minTotalMembers().longValue() : null;
            Long maxTotal = filter.maxTotalMembers() != null ? filter.maxTotalMembers().longValue() : null;

            applyMemberCountFilter(query, club, clubMember, totalMembersCondition, minTotal, maxTotal);
        }

        List<Club> clubs = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.fetchCount();

        return new PageImpl<>(clubs, pageable, total);
    }

    /**
     * 회원 수 필터 조건 적용
     */
    private void applyMemberCountFilter(
            JPAQuery<Club> query,
            QClub club,
            QClubMember clubMember,
            BooleanExpression memberCondition,
            Long minCount,
            Long maxCount
    ) {
        if (minCount != null) {
            query.where(
                    JPAExpressions
                            .select(clubMember.count())
                            .from(clubMember)
                            .where(memberCondition)
                            .groupBy(clubMember.club.id)
                            .having(clubMember.count().goe(minCount))
                            .exists()
            );
        }

        if (maxCount != null) {
            query.where(
                    JPAExpressions
                            .select(clubMember.count())
                            .from(clubMember)
                            .where(memberCondition)
                            .groupBy(clubMember.club.id)
                            .having(clubMember.count().loe(maxCount))
                            .exists()
            );
        }
    }
}