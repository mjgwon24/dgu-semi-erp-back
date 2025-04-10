package com.example.dgu_semi_erp_back.repository.peoplemanagement;


import com.example.dgu_semi_erp_back.dto.peoplemanagement.UserClubMemberDto.ClubMemberDetail;
import com.example.dgu_semi_erp_back.entity.club.ClubStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryRepository {
    Page<ClubMemberDetail> findClubMembers(String clubName, ClubStatus status, Pageable pageable);
}