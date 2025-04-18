package com.example.dgu_semi_erp_back.api.user;

import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.*;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.*;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.entity.club.ClubMember;
import com.example.dgu_semi_erp_back.entity.club.ClubStatus;
import com.example.dgu_semi_erp_back.exception.ClubNotFoundException;
import com.example.dgu_semi_erp_back.exception.UserNotFoundException;
import com.example.dgu_semi_erp_back.service.peoplemanagement.UserClubMemberService;
import com.example.dgu_semi_erp_back.service.user.UserService;
import com.example.dgu_semi_erp_back.usecase.club.ClubMemberCreateUseCase;
import com.example.dgu_semi_erp_back.usecase.user.UserUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;
    private final UserClubMemberService service;
    private final UserUpdateUseCase userUpdateUseCase;
    private final ClubMemberCreateUseCase clubMemberCreateUseCase;

    @GetMapping
    public Page<ClubMemberDetail> getClubMembers(
            @RequestParam(required = false) String clubName,
            @RequestParam(required = false) ClubStatus status,
            Pageable pageable
    ) {
        try{
            return service.getClubMembers(clubName, status, pageable);
        }
        catch(UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch(ClubNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUser(@CookieValue(name = "accessToken", required = true) String accessToken, @CookieValue(name = "refreshToken", required = true) String refreshToken){
        try{
            UserResponse user = userService.getUserByToken(accessToken);
            return ResponseEntity.ok(user);
        }
        catch (UserNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/me/club")
    public  ResponseEntity<ClubRegisterResponse> registerClub(@RequestBody ClubRegisterRequest clubRegisterDto,@CookieValue(name = "accessToken", required = true) String accessToken, @CookieValue(name = "refreshToken", required = true) String refreshToken){
        try{
            ClubRegisterResponse response = clubMemberCreateUseCase.createClubMember(clubRegisterDto,accessToken,refreshToken);
            return ResponseEntity.ok(response);
        }
        catch(UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch(ClubNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UserRoleUpdateResponse> changeUserRole(@PathVariable Long id,@RequestBody UserRoleUpdateRequest request,@CookieValue(name = "accessToken", required = true) String accessToken, @CookieValue(name = "refreshToken", required = true) String refreshToken){
        try{
            User updatedUser = userUpdateUseCase.updateRole(id,request,accessToken,refreshToken);
            return ResponseEntity.ok(UserRoleUpdateResponse.builder().message("수정 완료").role(request.role()).build());
        }
        catch(UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }
    @PatchMapping("/{id}/email")
    public ResponseEntity<UserEmailUpdateResponse> changeUserEmail(@PathVariable Long id, @RequestBody UserEmailUpdateRequest request,@CookieValue(name = "accessToken", required = true) String accessToken, @CookieValue(name = "refreshToken", required = true) String refreshToken){
        try{
            User updatedUser = userUpdateUseCase.updateEmail(id,request,accessToken,refreshToken);
            return ResponseEntity.ok(UserEmailUpdateResponse.builder().message("수정 완료").email(request.email()).build());
        }
        catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }



}
