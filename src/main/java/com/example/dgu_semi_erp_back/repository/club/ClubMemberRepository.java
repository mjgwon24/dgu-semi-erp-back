package com.example.dgu_semi_erp_back.repository.club;

import com.example.dgu_semi_erp_back.entity.club.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    Boolean existsClubMemberByUserIdAndClubId(Long userId, Long clubId);
    List<ClubMember> findClubIdsByUserId(Long userId);
    Optional<ClubMember> findClubMemberByUserIdAndClubId(Long userId, Long clubId);
}
