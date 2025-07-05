package com.example.dgu_semi_erp_back.api.club;

import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.*;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.*;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.entity.club.ClubStatus;
import com.example.dgu_semi_erp_back.entity.club.MemberStatus;
import com.example.dgu_semi_erp_back.exception.ClubNotFoundException;
import com.example.dgu_semi_erp_back.exception.UserNotFoundException;
import com.example.dgu_semi_erp_back.service.peoplemanagement.UserClubMemberService;
import com.example.dgu_semi_erp_back.service.user.UserService;
import com.example.dgu_semi_erp_back.usecase.user.UserUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubApi {

    private final UserService userService;
    private final UserUseCase userUseCase;
    private final UserClubMemberService clubMemberService;

    @GetMapping("/member")
    public ResponseEntity<ClubMemberDetailSearchResponse> getClubMembers(
            @RequestParam(required = true) Long clubId,
            @RequestParam(required = false) String status,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        var response = clubMemberService.getClubMembers(clubId, status, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ClubRegisterResponse> registerClub(
            @RequestBody ClubRegisterRequest clubRegisterDto,
            HttpServletRequest req
    ){
        String username = (String) req.getAttribute("username");
        var response = userService.createClubMember(clubRegisterDto,username);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping
    public ResponseEntity<ClubLeaveResponse> leaveClub(
            @RequestBody ClubLeaveRequest clubLeaveDto,
            HttpServletRequest req
    ){
        String username = (String) req.getAttribute("username");
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



}
