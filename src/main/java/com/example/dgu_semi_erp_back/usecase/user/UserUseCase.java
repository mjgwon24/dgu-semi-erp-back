package com.example.dgu_semi_erp_back.usecase.user;

import com.example.dgu_semi_erp_back.dto.auth.TokenResponse;
import com.example.dgu_semi_erp_back.dto.club.ClubDto.ClubResponse;
import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto;
import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.ClubMemberDetail;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.UserResponse;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.UserRoleUpdateRequest;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.UserEmailUpdateRequest;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.UserNameUpdateRequest;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.entity.club.ClubStatus;
import com.example.dgu_semi_erp_back.entity.club.MemberStatus;
import com.example.dgu_semi_erp_back.projection.club.ClubProjection.ClubSummary;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserUseCase {
    User updateRole(Long id,UserRoleUpdateRequest request,String username);
    User updateEmail(Long userId, UserEmailUpdateRequest request,String username);
    TokenResponse updateName(Long userId, UserNameUpdateRequest request, String username, HttpServletResponse response);
    User findUserByAccessToken(String accessToken);
    UserClubMemberDto.ClubMemberSearchResponse getUserClubs(
            String accessToken,
            Long currentPeopleMin,
            Long currentPeopleMax,
            Long totalPeopleMin,
            Long totalPeopleMax,
            String clubName,
            ClubStatus status,
            Pageable pageable);

    UserClubMemberDto.MemberDetailSearchResponse getAllClubs(
            String username,
            Long currentPeopleMin,
            Long currentPeopleMax,
            Long totalPeopleMin,
            Long totalPeopleMax,
            String clubName,
            ClubStatus status);
}
