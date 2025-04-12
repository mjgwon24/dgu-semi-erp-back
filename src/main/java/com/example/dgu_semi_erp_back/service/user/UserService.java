package com.example.dgu_semi_erp_back.service.user;

import com.example.dgu_semi_erp_back.common.exception.CustomException;
import com.example.dgu_semi_erp_back.common.exception.ErrorCode;
import com.example.dgu_semi_erp_back.common.jwt.JwtUtil;
import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto;
import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.*;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.*;
import com.example.dgu_semi_erp_back.entity.budget.BudgetPlan;
import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.entity.club.ClubMember;
import com.example.dgu_semi_erp_back.entity.club.Role;
import com.example.dgu_semi_erp_back.exception.ClubNotFoundException;
import com.example.dgu_semi_erp_back.exception.UserNotFoundException;
import com.example.dgu_semi_erp_back.mapper.UserClubMemberMapper;
import com.example.dgu_semi_erp_back.mapper.UserMapper;
import com.example.dgu_semi_erp_back.repository.club.ClubMemberRepository;
import com.example.dgu_semi_erp_back.repository.club.ClubRepository;
import com.example.dgu_semi_erp_back.repository.auth.UserRepository;
import com.example.dgu_semi_erp_back.usecase.club.ClubMemberCreateUseCase;
import com.example.dgu_semi_erp_back.usecase.user.UserUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService implements UserUpdateUseCase, ClubMemberCreateUseCase {
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final UserMapper userMapper;
    private final UserClubMemberMapper userClubMemberMapper;
    private final JwtUtil jwtutil;
    public User findUserByAccessToken(String accessToken) throws UserNotFoundException{
        String userName = jwtutil.getUsernameFromToken(accessToken);
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다. 1"));
        return user;
    }

    public UserResponse getUserByToken(String accessToken) throws UserNotFoundException{
        String userName = jwtutil.getUsernameFromToken(accessToken);
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
        Club clubID = clubRepository.findClubIdById(user.getId()).orElseThrow(() -> new ClubNotFoundException("존재하지 않는 동아리입니다."));
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .clubId(clubID.getId())
                .build();
    }
    @Transactional
    @Override
    public User updateRole(Long id, UserRoleUpdateRequest request,String accessToken,String refreshToken) throws UserNotFoundException,CustomException {
        User user = findUserByAccessToken(accessToken);
        Role userRole = user.getRole();
        if(userRole==Role.LEADER||userRole==Role.VICE_LEADER){
            User target_user = userRepository.findUserById(id)
                    .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
            userMapper.updateUserRole(target_user, request);
            return target_user;
        }
        else{
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
    }
    @Transactional
    @Override
    public User updateEmail(Long id,UserEmailUpdateRequest request,String accessToken,String refreshToken) throws UserNotFoundException,CustomException {
        try{
            User user = findUserByAccessToken(accessToken);
            User target_user = userRepository.findUserById(id)
                    .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
            userMapper.updateUserEmail(target_user, request);
            return target_user;
        }
        catch(Exception e){
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

    }
    @Transactional
    @Override
    public ClubRegisterResponse createClubMember(ClubRegisterRequest clubRegisterRequest,String accessToken,String refreshToken) throws ClubNotFoundException,UserNotFoundException {
        User user = findUserByAccessToken(accessToken);
        Club club = clubRepository.findClubIdById(clubRegisterRequest.clubId()).orElseThrow(() -> new ClubNotFoundException("존재하지 않는 동아리입니다."));
        if(clubMemberRepository.existsClubMemberByUserIdAndClubId(user.getId(), club.getId())){
            throw new CustomException(ErrorCode.DUPLICATED_MEMBER);
        }

        LocalDateTime now = LocalDateTime.now();
        System.out.println(club.getId());
        System.out.println(user.getId());
        ClubMember clubMember = userClubMemberMapper.toEntity(clubRegisterRequest,club.getId(),user.getId(),now);
        clubMemberRepository.save(clubMember);
        return ClubRegisterResponse.builder().message("가입 성공").build();
    }
}
