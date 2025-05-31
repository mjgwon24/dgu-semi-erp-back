package com.example.dgu_semi_erp_back.usecase.club;

import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto;

public interface ClubMemberUpdateUseCase {
    UserClubMemberDto.ClubLeaveResponse leaveClubMember(UserClubMemberDto.ClubLeaveRequest clubLeaveRequest, String username);
}
