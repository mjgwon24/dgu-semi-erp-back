package com.example.dgu_semi_erp_back.usecase.club;

import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.*;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.entity.club.ClubMember;

public interface ClubMemberCreateUseCase {
    ClubRegisterResponse createClubMember(ClubRegisterRequest clubRegisterRequest,String username);
}