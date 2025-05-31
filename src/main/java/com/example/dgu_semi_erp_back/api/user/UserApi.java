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
import jakarta.servlet.http.HttpServletRequest;
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
            HttpServletRequest request
    ){
        String username = (String) request.getAttribute("username");
        var user = userService.getUserInfo(username);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/me/club")
    public ResponseEntity<ClubMemberSearchResponse> getClubs(
            @PageableDefault(size = 5) Pageable pageable,
            HttpServletRequest request
    ){
        String username = (String) request.getAttribute("username");
        var response = userService.getUserClubs(username,pageable);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/me/club")
    public ResponseEntity<ClubRegisterResponse> registerClub(
            @RequestBody ClubRegisterRequest clubRegisterDto,
            HttpServletRequest request
    ){
        String username = (String) request.getAttribute("username");
        var response = userService.createClubMember(clubRegisterDto,username);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/me/club")
    public ResponseEntity<ClubLeaveResponse> leaveClub(
            @RequestBody ClubLeaveRequest clubLeaveDto,
            HttpServletRequest request
    ){
        String username = (String) request.getAttribute("username");
        var response = userService.leaveClubMember(clubLeaveDto,username);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UserRoleUpdateResponse> changeUserRole(
            @PathVariable Long id,
            @RequestBody UserRoleUpdateRequest request,
            HttpServletRequest req
    ){
        String username = (String) req.getAttribute("username");
        userUseCase.updateRole(id,request,username);
        return ResponseEntity.ok(UserRoleUpdateResponse.builder().message("수정 완료").role(request.role()).build());
    }
    @PatchMapping("/{id}/email")
    public ResponseEntity<UserEmailUpdateResponse> changeUserEmail(
            @PathVariable Long id,
            @RequestBody UserEmailUpdateRequest request,
            HttpServletRequest req
    ){
        String username = (String) req.getAttribute("username");
        userUseCase.updateEmail(id,request,username);
        return ResponseEntity.ok(UserEmailUpdateResponse.builder().message("수정 완료").email(request.email()).build());
    }



}
