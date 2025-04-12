package com.example.dgu_semi_erp_back.repository.club;

import com.example.dgu_semi_erp_back.entity.club.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    Boolean existsClubMemberByUserIdAndClubId(Long userId, Long clubId);
}
