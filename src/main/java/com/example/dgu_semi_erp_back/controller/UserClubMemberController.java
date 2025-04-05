package com.example.dgu_semi_erp_back.controller;

import com.example.dgu_semi_erp_back.dto.peoplemanagement.UserClubMemberDto.ClubMemberDetail;
import com.example.dgu_semi_erp_back.entity.club.MemberStatus;
import com.example.dgu_semi_erp_back.service.peoplemanagement.UserClubMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserClubMemberController {

    private final UserClubMemberService service;

    @GetMapping("/club-members")
    public Page<ClubMemberDetail> getClubMembers(
            @RequestParam(required = false) String clubName,
            @RequestParam(required = false) MemberStatus status,
            Pageable pageable
    ) {
        return service.getClubMembers(clubName, status, pageable);
    }
}