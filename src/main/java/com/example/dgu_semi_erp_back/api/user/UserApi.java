package com.example.dgu_semi_erp_back.api.user;

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
    private final UserUseCase userUseCase;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUser(
            @CookieValue(name = "accessToken", required = true) String accessToken,
            @CookieValue(name = "refreshToken", required = true) String refreshToken
    ){
        var user = userService.getUserByToken(accessToken);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/me/club")
    public ResponseEntity<ClubMemberSearchResponse> getClubs(
            @CookieValue(name = "accessToken", required = true) String accessToken,
            @CookieValue(name = "refreshToken", required = true) String refreshToken,
            @PageableDefault(size = 5) Pageable pageable
    ){
        var response = userService.getUserClubs(accessToken,pageable);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/me/club")
    public ResponseEntity<ClubRegisterResponse> registerClub(
            @RequestBody ClubRegisterRequest clubRegisterDto,
            @CookieValue(name = "accessToken", required = true) String accessToken,
            @CookieValue(name = "refreshToken", required = true) String refreshToken
    ){
        var response = userService.createClubMember(clubRegisterDto,accessToken,refreshToken);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/me/club")
    public ResponseEntity<ClubLeaveResponse> leaveClub(
            @RequestBody ClubLeaveRequest clubLeaveDto,
            @CookieValue(name = "accessToken", required = true) String accessToken,
            @CookieValue(name = "refreshToken", required = true) String refreshToken
    ){
        var response = userService.leaveClubMember(clubLeaveDto,accessToken,refreshToken);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UserRoleUpdateResponse> changeUserRole(
            @PathVariable Long id,
            @RequestBody UserRoleUpdateRequest request,
            @CookieValue(name = "accessToken", required = true) String accessToken,
            @CookieValue(name = "refreshToken", required = true) String refreshToken
    ){
        userUseCase.updateRole(id,request,accessToken,refreshToken);
        return ResponseEntity.ok(UserRoleUpdateResponse.builder().message("수정 완료").role(request.role()).build());
    }
    @PatchMapping("/{id}/email")
    public ResponseEntity<UserEmailUpdateResponse> changeUserEmail(
            @PathVariable Long id,
            @RequestBody UserEmailUpdateRequest request,
            @CookieValue(name = "accessToken", required = true) String accessToken,
            @CookieValue(name = "refreshToken", required = true) String refreshToken
    ){
        userUseCase.updateEmail(id,request,accessToken,refreshToken);
        return ResponseEntity.ok(UserEmailUpdateResponse.builder().message("수정 완료").email(request.email()).build());
    }



}
