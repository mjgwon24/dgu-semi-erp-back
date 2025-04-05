package com.example.dgu_semi_erp_back.service.peoplemanagement;

import com.example.dgu_semi_erp_back.dto.peoplemanagement.UserClubMemberDto.ClubMemberDetail;
import com.example.dgu_semi_erp_back.entity.club.ClubStatus;
import com.example.dgu_semi_erp_back.repository.peoplemanagement.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserClubMemberService {

    private final UserQueryRepository queryRepository;

    public Page<ClubMemberDetail> getClubMembers(String clubName, ClubStatus status, Pageable pageable) {
        return queryRepository.findClubMembers(clubName, status, pageable);
    }
}