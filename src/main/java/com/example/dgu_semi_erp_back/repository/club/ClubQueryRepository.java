package com.example.dgu_semi_erp_back.repository.club;

import com.example.dgu_semi_erp_back.entity.club.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubQueryRepository {
    Page<Club> getPagedAccounts(Pageable pageable);
}
