package com.example.dgu_semi_erp_back.repository.club;

import com.example.dgu_semi_erp_back.entity.account.QAccount;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.entity.club.QClub;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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
}
