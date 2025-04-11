package com.example.dgu_semi_erp_back.repository.account;

import com.example.dgu_semi_erp_back.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 데이터 조회
public interface AccountQueryRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByClubId(Long clubId);
}
