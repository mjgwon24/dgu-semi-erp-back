package com.example.dgu_semi_erp_back.api.user;
import jakarta.servlet.http.HttpServletRequest;

import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.*;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.*;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.entity.club.ClubStatus;
import com.example.dgu_semi_erp_back.exception.ClubNotFoundException;
import com.example.dgu_semi_erp_back.exception.UserNotFoundException;
import com.example.dgu_semi_erp_back.projection.club.ClubProjection.ClubSummary;
import com.example.dgu_semi_erp_back.service.peoplemanagement.UserClubMemberService;
import com.example.dgu_semi_erp_back.service.user.UserService;
import com.example.dgu_semi_erp_back.usecase.club.ClubMemberCreateUseCase;
import com.example.dgu_semi_erp_back.usecase.club.ClubMemberUpdateUseCase;
import com.example.dgu_semi_erp_back.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;
    private final UserClubMemberService clubMemberService;
    private final UserUseCase userUseCase;

    @GetMapping
    public ResponseEntity<ClubMemberDetailSearchResponse> getClubMembers(
            @RequestParam(required = true) Long clubId,
            @RequestParam(required = true) String status,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        var response = clubMemberService.getClubMembers(clubId, status, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUser(
            @RequestHeader(name = "Authorization", required = false) String authorizationHeader
    ) {
        String accessToken = extractAccessToken(authorizationHeader);
        var user = userService.getUserByToken(accessToken);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/me/club")
    public ResponseEntity<ClubMemberSearchResponse> getClubs(
            HttpServletRequest request,
            @PageableDefault(size = 5) Pageable pageable
    ){
        String accessToken = extractAccessToken(request);
        String refreshToken = extractRefreshToken(request);
        var response = userService.getUserClubs(accessToken, pageable);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/me/club")
    public ResponseEntity<ClubRegisterResponse> registerClub(
            @RequestBody ClubRegisterRequest clubRegisterDto,
            HttpServletRequest request
    ) {
        String accessToken = extractAccessToken(request);
        String refreshToken = extractRefreshToken(request);
        var response = userService.createClubMember(clubRegisterDto, accessToken, refreshToken);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/me/club")
    public ResponseEntity<ClubLeaveResponse> leaveClub(
            @RequestBody ClubLeaveRequest clubLeaveDto,
            HttpServletRequest request
    ){
        String accessToken = extractAccessToken(request);
        String refreshToken = extractRefreshToken(request);
        var response = userService.leaveClubMember(clubLeaveDto, accessToken, refreshToken);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UserRoleUpdateResponse> changeUserRole(
            @PathVariable Long id,
            @RequestBody UserRoleUpdateRequest userRoleUpdateRequest,
            HttpServletRequest request
    ){
        String accessToken = extractAccessToken(request);
        String refreshToken = extractRefreshToken(request);
        userUseCase.updateRole(id, userRoleUpdateRequest, accessToken, refreshToken);
        return ResponseEntity.ok(UserRoleUpdateResponse.builder().message("수정 완료").role(userRoleUpdateRequest.role()).build());
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PatchMapping("/{id}/email")
    public ResponseEntity<UserEmailUpdateResponse> changeUserEmail(
            @PathVariable Long id,
            @RequestBody UserEmailUpdateRequest userEmailUpdateRequest,
            HttpServletRequest request
    ){
        String accessToken = extractAccessToken(request);
        String refreshToken = extractRefreshToken(request);
        userUseCase.updateEmail(id, userEmailUpdateRequest, accessToken, refreshToken);
        return ResponseEntity.ok(UserEmailUpdateResponse.builder().message("수정 완료").email(userEmailUpdateRequest.email()).build());
    }



    private String extractAccessToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return extractAccessToken(authHeader);
    }

    private String extractAccessToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new RuntimeException("access_token이 Authorization 헤더에 없습니다.");
    }

    private String extractRefreshToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if ("refresh_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        throw new RuntimeException("refresh_token 쿠키가 없습니다.");
    }
}