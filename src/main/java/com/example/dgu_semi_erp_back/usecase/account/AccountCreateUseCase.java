package com.example.dgu_semi_erp_back.usecase.account;

import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountCreateRequest;
import com.example.dgu_semi_erp_back.entity.account.Account;
import com.example.dgu_semi_erp_back.entity.account.AccountHistory;
import org.springframework.data.domain.Page;

public interface AccountCreateUseCase {
    Account create(AccountCreateRequest request);
    Account getAccountByClubId(Long clubId);
    Page<AccountHistory> getPagedAccountHistories(
            Long accountId,
            int page,
            int size
    );
}
