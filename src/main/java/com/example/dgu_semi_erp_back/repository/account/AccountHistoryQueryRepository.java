package com.example.dgu_semi_erp_back.repository.account;

import com.example.dgu_semi_erp_back.entity.account.AccountHistory;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface AccountHistoryQueryRepository {
    Page<AccountHistory> findPagedHistoriesByAccountId(Long accountId, Pageable pageable);
}
