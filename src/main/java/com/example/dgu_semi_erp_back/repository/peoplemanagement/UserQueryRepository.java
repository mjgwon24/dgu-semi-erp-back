package com.example.dgu_semi_erp_back.repository.peoplemanagement;


import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.ClubMemberDetail;
import com.example.dgu_semi_erp_back.entity.club.ClubMember;
import com.example.dgu_semi_erp_back.entity.club.ClubStatus;
import com.example.dgu_semi_erp_back.entity.club.MemberStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQueryRepository  extends JpaRepository<ClubMember, Long> {
//    Page<ClubMember> findClubMembers(String clubName, ClubStatus status, Pageable pageable);
    Page<ClubMember> findClubMembersByClubIdAndStatus(Long Id, MemberStatus status, Pageable pageable);
}