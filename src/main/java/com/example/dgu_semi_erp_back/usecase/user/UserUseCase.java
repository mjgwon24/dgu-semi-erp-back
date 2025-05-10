package com.example.dgu_semi_erp_back.usecase.user;

import com.example.dgu_semi_erp_back.dto.club.ClubDto.ClubResponse;
import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto;
import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.ClubMemberDetail;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.UserResponse;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.UserRoleUpdateRequest;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.UserEmailUpdateRequest;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.projection.club.ClubProjection.ClubSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserUseCase {
    User updateRole(Long id,UserRoleUpdateRequest request,String accessToken,String refreshToken);
    User updateEmail(Long userId, UserEmailUpdateRequest request, String accessToken, String refreshToken);
    User findUserByAccessToken(String accessToken);
    UserResponse getUserByToken(String accessToken);
    UserClubMemberDto.ClubMemberSearchResponse getUserClubs(String accessToken, Pageable pageable);
}
