package com.example.dgu_semi_erp_back.repository.club;

import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.ClubAccountFilter;
import com.example.dgu_semi_erp_back.entity.account.QAccount;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.entity.club.ClubStatus;
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

        BooleanExpression hasActiveAccount = hasActiveAccount(club, account);

        List<Club> clubs = queryFactory
                .selectFrom(club)
                .where(hasActiveAccount)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(club.countDistinct())
                .from(club)
                .where(hasActiveAccount)
                .fetchOne();

        return new PageImpl<>(clubs, pageable, total != null ? total : 0L);
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

        // 통장을 보유한 동아리
        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(hasActiveAccount(club, account));

        // 필터 조건
        BooleanExpression statusCondition = hasStatus(filter.status());
        BooleanExpression nameCondition = hasNameContaining(filter.clubName());

        if (statusCondition != null) {
            whereClause.and(statusCondition);
        }

        if (nameCondition != null) {
            whereClause.and(nameCondition);
        }

        // 기본 쿼리 구성
        JPAQuery<Club> query = queryFactory.selectFrom(club).where(whereClause);

        // 활동 인원 필터
        applyActiveMembersFilter(query, club, clubMember, filter);

        // 누적 인원 필터
        applyTotalMembersFilter(query, club, clubMember, filter);

        List<Club> clubs = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 카운트 쿼리 구성 (동일한 필터 조건 적용)
        JPAQuery<Long> countQuery = queryFactory
                .select(club.countDistinct())
                .from(club)
                .where(whereClause);

        applyActiveMembersFilter(countQuery, club, clubMember, filter);
        applyTotalMembersFilter(countQuery, club, clubMember, filter);

        Long total = countQuery.fetchOne();

        return new PageImpl<>(clubs, pageable, total != null ? total : 0L);
    }

    /**
     * 활성화된 통장을 보유한 동아리 조건
     */
    private BooleanExpression hasActiveAccount(QClub club, QAccount account) {
        Instant now = Instant.now();
        return JPAExpressions
                .selectOne()
                .from(account)
                .where(
                        account.club.eq(club),
                        account.deletedAt.isNull()
                                .or(account.deletedAt.gt(now))
                )
                .exists();
    }

    /**
     * 동아리 상태 조건
     */
    private BooleanExpression hasStatus(ClubStatus status) {
        return status != null ? QClub.club.status.eq(status) : null;
    }

    /**
     * 동아리명 부분 일치 조건
     */
    private BooleanExpression hasNameContaining(String name) {
        return StringUtils.hasText(name) ? QClub.club.name.containsIgnoreCase(name) : null;
    }

    /**
     * 활동 인원 필터 적용
     */
    private <T> void applyActiveMembersFilter(JPAQuery<T> query, QClub club, QClubMember clubMember, ClubAccountFilter filter) {
        if (filter.minActiveMembers() != null || filter.maxActiveMembers() != null) {
            BooleanExpression activeMembersCondition = club.id.eq(clubMember.club.id)
                    .and(clubMember.status.eq(MemberStatus.ACTIVE));

            Long minActive = filter.minActiveMembers() != null ? filter.minActiveMembers().longValue() : null;
            Long maxActive = filter.maxActiveMembers() != null ? filter.maxActiveMembers().longValue() : null;

            applyMemberCountFilter(query, club, clubMember, activeMembersCondition, minActive, maxActive);
        }
    }

    /**
     * 누적 인원 필터 적용
     */
    private <T> void applyTotalMembersFilter(JPAQuery<T> query, QClub club, QClubMember clubMember, ClubAccountFilter filter) {
        if (filter.minTotalMembers() != null || filter.maxTotalMembers() != null) {
            BooleanExpression totalMembersCondition = club.id.eq(clubMember.club.id);

            Long minTotal = filter.minTotalMembers() != null ? filter.minTotalMembers().longValue() : null;
            Long maxTotal = filter.maxTotalMembers() != null ? filter.maxTotalMembers().longValue() : null;

            applyMemberCountFilter(query, club, clubMember, totalMembersCondition, minTotal, maxTotal);
        }
    }

    /**
     * 회원 수 필터 조건 적용
     */
    private <T> void applyMemberCountFilter(
            JPAQuery<T> query,
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