package com.example.dgu_semi_erp_back.service.user;

import com.example.dgu_semi_erp_back.common.exception.CustomException;
import com.example.dgu_semi_erp_back.common.exception.ErrorCode;
import com.example.dgu_semi_erp_back.common.jwt.JwtUtil;
import com.example.dgu_semi_erp_back.dto.club.ClubDto.ClubResponse;
import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.*;
import com.example.dgu_semi_erp_back.dto.common.PaginationInfo;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.*;
import com.example.dgu_semi_erp_back.entity.club.*;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.exception.ClubNotFoundException;
import com.example.dgu_semi_erp_back.exception.UserNotFoundException;
import com.example.dgu_semi_erp_back.mapper.UserClubMemberMapper;
import com.example.dgu_semi_erp_back.mapper.UserMapper;
import com.example.dgu_semi_erp_back.projection.club.ClubMemberProjection;
import com.example.dgu_semi_erp_back.projection.club.ClubProjection.ClubSummary;
import com.example.dgu_semi_erp_back.repository.club.ClubMemberRepository;
import com.example.dgu_semi_erp_back.repository.club.ClubRepository;
import com.example.dgu_semi_erp_back.repository.auth.UserRepository;
import com.example.dgu_semi_erp_back.usecase.club.ClubMemberCreateUseCase;
import com.example.dgu_semi_erp_back.usecase.user.UserUseCase;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase, ClubMemberCreateUseCase {
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final UserMapper userMapper;
    private final UserClubMemberMapper userClubMemberMapper;
    private final JwtUtil jwtutil;
    private final JPAQueryFactory queryFactory;
    @Override
    public ClubMemberSearchResponse getUserClubs(String accessToken, Pageable pageable) {
        String userName = jwtutil.getUsernameFromToken(accessToken);
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다.0"));
        Page<ClubMember> clubMemberPage = clubMemberRepository.findClubMembersByUserId(user.getId(), pageable);
        List<Long> clubIds = clubMemberPage.stream()
                .map(cm -> cm.getClub().getId())
                .collect(Collectors.toList());
        if (clubIds.isEmpty()) {
            return ClubMemberSearchResponse.builder()
                    .content(null)
                    .paginationInfo(new PaginationInfo(
                            clubMemberPage.getNumber(),
                            clubMemberPage.getSize(),
                            clubMemberPage.getTotalPages(),
                            clubMemberPage.getTotalElements()
                    ))
                    .build();
        }
        QClub qClub = QClub.club;
        QClubMember qMember = QClubMember.clubMember;

        List<ClubSummary> clubSummeryList = queryFactory
                .select(Projections.constructor(
                        ClubSummary.class,
                        qClub.id,
                        qClub.name,
                        qClub.affiliation,
                        qClub.status,
                        Projections.constructor(
                                ClubMemberProjection.ClubMemberSummery.class,
                                qMember.id,
                                qMember.role,
                                qMember.status,
                                qMember.registeredAt
                        )
                ))
                .from(qClub)
                .join(qMember).on(qMember.club.id.eq(qClub.id))
                .where(qClub.id.in(clubIds).and(qMember.user.id.eq(user.getId())))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return ClubMemberSearchResponse.builder()
                .content(clubSummeryList)
                .paginationInfo(new PaginationInfo(
                        clubMemberPage.getNumber(),
                        clubMemberPage.getSize(),
                        clubMemberPage.getTotalPages(),
                        clubMemberPage.getTotalElements()
                ))
            .build();
    }

    @Override
    public User findUserByAccessToken(String accessToken) throws UserNotFoundException{
        String userName = jwtutil.getUsernameFromToken(accessToken);
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다. 1"));
        return user;
    }




    @Override
    public UserResponse getUserByToken(String accessToken) throws UserNotFoundException{
        String userName = jwtutil.getUsernameFromToken(accessToken);
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다.1"));
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getUsername())
                .email(user.getEmail())
                .build();

    }


    @Transactional
    @Override
    public User updateRole(Long id, UserRoleUpdateRequest request,String accessToken,String refreshToken) throws UserNotFoundException,CustomException {
        User user = findUserByAccessToken(accessToken);
        Club club = clubRepository.findClubIdById(request.clubId()).orElseThrow(() -> new UserNotFoundException("존재하지 않는 동아리입니다."));
        Role userRole = clubMemberRepository.findClubMemberByUserIdAndClubId(user.getId(),request.clubId()).orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다.")).getRole();
        if(userRole==Role.LEADER||userRole==Role.VICE_LEADER|| Objects.equals(user.getId(), id)){
            User target_user = userRepository.findUserById(id)
                    .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
            ClubMember clubMember = clubMemberRepository.findClubMemberByUserIdAndClubId(target_user.getId(),club.getId()).orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
            ClubMember newClubMember = userClubMemberMapper.toEntity(clubMember,request);
            return clubMemberRepository.save(newClubMember).getUser();
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
            ClubMember clubMember = clubMemberRepository.findClubMemberByUserIdAndClubId(id,user.getId()).orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));;

            User target_user = userRepository.findUserById(id)
                    .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
            if(Objects.equals(user.getId(), target_user.getId())||clubMember.getRole()==Role.LEADER||clubMember.getRole()==Role.VICE_LEADER){
                User newUser = userMapper.toEntity(target_user, request);
                return userRepository.save(newUser);
            }
            else{
                throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
            }

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
        ClubMember clubMember = userClubMemberMapper.toEntity(clubRegisterRequest,club,user,now);
        clubMemberRepository.save(clubMember);
        return ClubRegisterResponse.builder().message("가입 신청 성공").club(club).build();
    }
}
