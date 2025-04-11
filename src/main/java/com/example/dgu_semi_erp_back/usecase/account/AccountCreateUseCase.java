package com.example.dgu_semi_erp_back.usecase.account;

import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountCreateRequest;
import com.example.dgu_semi_erp_back.entity.account.Account;

public interface AccountCreateUseCase {
    Account create(AccountCreateRequest request);
    Account getAccountByClubId(Long clubId);
}
