package com.example.dgu_semi_erp_back.repository.account;

import com.example.dgu_semi_erp_back.entity.account.AccountHistory;
import com.example.dgu_semi_erp_back.entity.account.QAccountHistory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class AccountHistoryQueryRepositoryImpl implements AccountHistoryQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AccountHistory> findPagedHistoriesByAccountId(Long accountId, Pageable pageable) {
        QAccountHistory accountHistory = QAccountHistory.accountHistory;

        List<AccountHistory> content = queryFactory.selectFrom(accountHistory)
                .where(accountHistory.account.id.eq(accountId))
                .orderBy(accountHistory.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalResult = queryFactory
                .select(accountHistory.count())
                .from(accountHistory)
                .where(accountHistory.account.id.eq(accountId))
                .fetchOne();

        long total = totalResult != null ? totalResult : 0L;

        return new PageImpl<>(content, pageable, total);
    }
}
