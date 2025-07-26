package com.example.dgu_semi_erp_back.usecase.account;

import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.ClubAccountFilter;
import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountUpdateRequest;
import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountCreateRequest;
import com.example.dgu_semi_erp_back.entity.account.Account;
import com.example.dgu_semi_erp_back.entity.account.AccountHistory;
import com.example.dgu_semi_erp_back.entity.club.Club;
import org.springframework.data.domain.Page;

public interface AccountUseCase {
    Account createAccount(AccountCreateRequest request, String username);
    Account getAccountByClubId(Long clubId);

    Page<AccountHistory> getPagedAccountHistories(
            Long accountId,
            int page,
            int size
    );

    Account updateAccount(
            Long accountId,
            AccountUpdateRequest request
    );

    void deleteAccount(Long accountId);

    Page<Club> getPagedAccounts(
            int page,
            int size
    );

    Page<Club> getPagedAccountsWithFilter(int page, int size, ClubAccountFilter filter);
}
