package com.example.dgu_semi_erp_back.repository.club;

import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto;
import com.example.dgu_semi_erp_back.entity.club.ClubMember;
import com.example.dgu_semi_erp_back.entity.club.ClubStatus;
import com.example.dgu_semi_erp_back.projection.club.ClubProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    Boolean existsClubMemberByUserIdAndClubId(Long userId, Long clubId);
    List<ClubMember> findClubIdsByUserId(Long userId);
    Optional<ClubMember> findClubMemberByUserIdAndClubId(Long userId, Long clubId);
    Page<ClubMember> findClubMembersByUserId(Long userId, Pageable pageable);
}
