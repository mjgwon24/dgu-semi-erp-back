package com.example.dgu_semi_erp_back.repository.account;

import com.example.dgu_semi_erp_back.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountCommandRepository extends JpaRepository<Account, Long> {
}
