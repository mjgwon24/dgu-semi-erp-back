package com.example.dgu_semi_erp_back.service.user;

import com.example.dgu_semi_erp_back.common.exception.CustomException;
import com.example.dgu_semi_erp_back.common.exception.ErrorCode;
import com.example.dgu_semi_erp_back.common.jwt.JwtUtil;
import com.example.dgu_semi_erp_back.dto.club.ClubDto.ClubResponse;
import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.*;
import com.example.dgu_semi_erp_back.dto.common.PaginationInfo;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.*;
import com.example.dgu_semi_erp_back.entity.auth.user.QUser;
import com.example.dgu_semi_erp_back.entity.auth.user.UserRole;
import com.example.dgu_semi_erp_back.entity.club.*;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.exception.ClubNotFoundException;
import com.example.dgu_semi_erp_back.exception.UserNotFoundException;
import com.example.dgu_semi_erp_back.mapper.UserClubMemberMapper;
import com.example.dgu_semi_erp_back.mapper.UserMapper;
import com.example.dgu_semi_erp_back.projection.club.ClubMemberProjection;
import com.example.dgu_semi_erp_back.projection.club.ClubProjection;
import com.example.dgu_semi_erp_back.projection.club.ClubProjection.ClubSummary;
import com.example.dgu_semi_erp_back.repository.club.ClubMemberRepository;
import com.example.dgu_semi_erp_back.repository.club.ClubRepository;
import com.example.dgu_semi_erp_back.repository.auth.UserRepository;
import com.example.dgu_semi_erp_back.usecase.club.ClubMemberCreateUseCase;
import com.example.dgu_semi_erp_back.usecase.club.ClubMemberUpdateUseCase;
import com.example.dgu_semi_erp_back.usecase.user.UserUseCase;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.example.dgu_semi_erp_back.entity.auth.user.Major;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase, ClubMemberCreateUseCase, ClubMemberUpdateUseCase {
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final UserMapper userMapper;
    private final UserClubMemberMapper userClubMemberMapper;
    private final JwtUtil jwtutil;
    private final JPAQueryFactory queryFactory;
    @Override
    public ClubMemberSearchResponse getUserClubs(
            String username,
            Long currentPeopleMin,
            Long currentPeopleMax,
            Long totalPeopleMin,
            Long totalPeopleMax,
            String clubName,
            ClubStatus status,
            Pageable pageable) {
        // pageable이 null이면 전체 조회
        boolean isUnpaged = (pageable == null);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));

        QClubMember cm = QClubMember.clubMember;
        QClub club = QClub.club;

        List<Long> userClubIds = queryFactory
                .select(cm.club.id)
                .from(cm)
                .where(cm.user.id.eq(user.getId()))
                .fetch();

        Map<Long, Long> currentPeopleMap = queryFactory
                .select(cm.club.id, cm.count())
                .from(cm)
                .where(cm.status.eq(MemberStatus.ACTIVE))
                .groupBy(cm.club.id)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(0, Long.class),
                        tuple -> tuple.get(1, Long.class)
                ));

        Map<Long, Long> totalPeopleMap = queryFactory
                .select(cm.club.id, cm.count())
                .from(cm)
                .groupBy(cm.club.id)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(0, Long.class),
                        tuple -> tuple.get(1, Long.class)
                ));

        List<Long> filteredClubIds = userClubIds.stream()
                .filter(id -> {
                    long current = currentPeopleMap.getOrDefault(id, 0L);
                    long total = totalPeopleMap.getOrDefault(id, 0L);
                    return (currentPeopleMin == null || current >= currentPeopleMin) &&
                            (currentPeopleMax == null || current <= currentPeopleMax) &&
                            (totalPeopleMin == null || total >= totalPeopleMin) &&
                            (totalPeopleMax == null || total <= totalPeopleMax);
                })
                .collect(Collectors.toList());

        if (filteredClubIds.isEmpty()) {
            if(isUnpaged) {
                return ClubMemberSearchResponse.builder()
                        .content(new ArrayList<>())
                        .paginationInfo(new PaginationInfo(
                                0,
                                0,
                                0,
                                0
                        ))
                        .build();
            }
            else{
                return ClubMemberSearchResponse.builder()
                        .content(new ArrayList<>())
                        .paginationInfo(new PaginationInfo(
                                pageable.getPageNumber(),
                                pageable.getPageSize(),
                                0,
                                0
                        ))
                        .build();
            }
        }

        BooleanBuilder clubFilter = new BooleanBuilder();
        clubFilter.and(club.id.in(filteredClubIds));
        if(clubName != null && !clubName.isBlank()) {
            clubFilter.and(club.name.eq(clubName));
        }
        if(status != null) {
            clubFilter.and(club.status.eq(status));
        }
        List<Long> pagedClubIds;
        if(isUnpaged) {
            pagedClubIds = queryFactory
                    .select(club.id)
                    .from(club)
                    .where(clubFilter)
                    .orderBy(club.id.asc())
                    .fetch();
        }
        else{
            pagedClubIds = queryFactory
                    .select(club.id)
                    .from(club)
                    .where(clubFilter)
                    .orderBy(club.id.asc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }


        if(pagedClubIds.isEmpty()) {
            if(isUnpaged) {
                return ClubMemberSearchResponse.builder()
                        .content(new ArrayList<>())
                        .paginationInfo(new PaginationInfo(
                                0,
                                0,
                                0,
                                0
                        ))
                        .build();
            }
            else{
                return ClubMemberSearchResponse.builder()
                        .content(new ArrayList<>())
                        .paginationInfo(new PaginationInfo(
                                pageable.getPageNumber(),
                                pageable.getPageSize(),
                                0,
                                0
                        ))
                        .build();
            }

        }

        QClubMember qMember = QClubMember.clubMember;
        QUser qUser = QUser.user;

        List<Tuple> tuples = queryFactory
                .select(
                        club.id,
                        club.name,
                        club.affiliation,
                        club.status,
                        qMember.id,
                        qUser.username,
                        qUser.major,
                        qUser.studentNumber,
                        qMember.role,
                        qMember.status,
                        qMember.registeredAt
                )
                .from(club)
                .join(qMember).on(qMember.club.id.eq(club.id))
                .join(qUser).on(qMember.user.id.eq(qUser.id))
                .where(club.id.in(pagedClubIds))
                .orderBy(club.id.asc())
                .fetch();

        Map<Long, ClubProjection.ClubSummary> clubSummaryMap = new LinkedHashMap<>();

        for (Tuple tuple : tuples) {
            Long clubId = tuple.get(qClub.id);
            Major majorEnum = tuple.get(qUser.major);
            String majorLabel = majorEnum != null ? majorEnum.getLabel() : null;
            ClubMemberProjection.ClubMemberSummery member = new ClubMemberProjection.ClubMemberSummery(
                    tuple.get(qUser.id),
                    tuple.get(qMember.id),
                    tuple.get(qUser.username),
                    majorLabel,
                    tuple.get(qUser.studentNumber),
                    tuple.get(qMember.role),
                    tuple.get(qMember.status),
                    tuple.get(qMember.registeredAt)
            );

            clubSummaryMap.computeIfAbsent(clubId, id -> new ClubProjection.ClubSummary(
                    tuple.get(club.id),
                    tuple.get(club.name),
                    tuple.get(club.affiliation),
                    tuple.get(club.status),
                    new ArrayList<>()
            ));
            clubSummaryMap.get(clubId).clubMembers().add(member);
        }
        int totalClubs;
        if(status==null){
            totalClubs = totalPeopleMap.size();
        }
        else if(status==ClubStatus.ACTIVE){
            totalClubs = currentPeopleMap.size();
        }
        else{
            totalClubs = totalPeopleMap.size()-currentPeopleMap.size();
        }
        if(isUnpaged){
            return ClubMemberSearchResponse.builder()
                    .content(new ArrayList<>(clubSummaryMap.values()))
                    .paginationInfo(new PaginationInfo(
                            0,
                            0,
                            0,
                            totalClubs
                    ))
                    .build();
        }
        else{
            int totalPages = (int) Math.ceil((double) totalClubs / pageable.getPageSize());

            return ClubMemberSearchResponse.builder()
                    .content(new ArrayList<>(clubSummaryMap.values()))
                    .paginationInfo(new PaginationInfo(
                            pageable.getPageNumber(),
                            pageable.getPageSize(),
                            totalPages,
                            totalClubs
                    ))
                    .build();
        }
    }
    @Override
    public ClubSearchResponse getAllClubs(
            String username,
            Long currentPeopleMin,
            Long currentPeopleMax,
            Long totalPeopleMin,
            Long totalPeopleMax,
            String clubName,
            ClubStatus status) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));

        QClubMember cm = QClubMember.clubMember;
        QClub club = QClub.club;

        List<Long> userClubIds = queryFactory
                .select(cm.club.id)
                .from(cm)
                .where(cm.user.id.eq(user.getId()))
                .fetch();

        Map<Long, Long> currentPeopleMap = queryFactory
                .select(cm.club.id, cm.count())
                .from(cm)
                .where(cm.status.eq(MemberStatus.ACTIVE))
                .groupBy(cm.club.id)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(0, Long.class),
                        tuple -> tuple.get(1, Long.class)
                ));

        Map<Long, Long> totalPeopleMap = queryFactory
                .select(cm.club.id, cm.count())
                .from(cm)
                .groupBy(cm.club.id)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(0, Long.class),
                        tuple -> tuple.get(1, Long.class)
                ));

        List<Long> filteredClubIds = userClubIds.stream()
                .filter(id -> {
                    long current = currentPeopleMap.getOrDefault(id, 0L);
                    long total = totalPeopleMap.getOrDefault(id, 0L);
                    return (currentPeopleMin == null || current >= currentPeopleMin) &&
                            (currentPeopleMax == null || current <= currentPeopleMax) &&
                            (totalPeopleMin == null || total >= totalPeopleMin) &&
                            (totalPeopleMax == null || total <= totalPeopleMax);
                })
                .collect(Collectors.toList());

        if (filteredClubIds.isEmpty()) {
            return ClubSearchResponse.builder()
                    .content(new ArrayList<>())
                    .build();
        }

        BooleanBuilder clubFilter = new BooleanBuilder();
        clubFilter.and(club.id.in(filteredClubIds));
        if(clubName != null && !clubName.isBlank()) {
            clubFilter.and(club.name.eq(clubName));
        }
        if(status != null) {
            clubFilter.and(club.status.eq(status));
        }
        List<Long> pagedClubIds = queryFactory
                .select(club.id)
                .from(club)
                .where(clubFilter)
                .orderBy(club.id.asc())
                .fetch();


        if(pagedClubIds.isEmpty()) {
            return ClubSearchResponse.builder()
                .content(new ArrayList<>())
                .build();

        }

        QClubMember qMember = QClubMember.clubMember;
        QUser qUser = QUser.user;

        List<Tuple> tuples = queryFactory
                .select(
                        club.id,
                        club.name,
                        club.affiliation,
                        club.status,
                        qMember.id,
                        qUser.username,
                        qUser.major,
                        qUser.studentNumber,
                        qMember.role,
                        qMember.status,
                        qMember.registeredAt
                )
                .from(club)
                .join(qMember).on(qMember.club.id.eq(club.id))
                .join(qUser).on(qMember.user.id.eq(qUser.id))
                .where(club.id.in(pagedClubIds))
                .orderBy(club.id.asc())
                .fetch();

        Map<Long, ClubProjection.ClubDetail> clubDetailMap = new LinkedHashMap<>();

        for (Tuple tuple : tuples) {
            Long clubId = tuple.get(club.id);
            clubDetailMap.computeIfAbsent(clubId, id -> new ClubProjection.ClubDetail(
                    tuple.get(club.id),
                    tuple.get(club.name),
                    tuple.get(club.affiliation),
                    tuple.get(club.status)
            ));
        }
        return ClubSearchResponse.builder()
                .content(new ArrayList<>(clubDetailMap.values()))
                .build();
    }
    public User getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
        return user;
    }
    public UserResponse getUserInfo(String username) {
        User user = getUser(username);
        return UserResponse.builder().id(user.getId()).email(user.getEmail()).name(user.getUsername()).build();
    }

    @Override
    public User findUserByAccessToken(String accessToken) throws UserNotFoundException{
        String userName = jwtutil.getUsernameFromToken(accessToken);
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
        return user;
    }


    @Transactional
    @Override
    public User updateRole(Long id, UserRoleUpdateRequest request,String username) throws UserNotFoundException,CustomException {
        User user = getUser(username);
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
    public User updateEmail(Long userId,UserEmailUpdateRequest request,String username) throws UserNotFoundException,CustomException {
        try{
            User user = getUser(username);
            User target_user = userRepository.findUserById(userId)
                    .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
            if(Objects.equals(user.getId(), target_user.getId())||user.getRole()== UserRole.ADMIN){
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
    public ClubRegisterResponse createClubMember(ClubRegisterRequest clubRegisterRequest,String username) throws ClubNotFoundException,UserNotFoundException {
        User user = getUser(username);
        Club club = clubRepository.findClubIdById(clubRegisterRequest.clubId()).orElseThrow(() -> new ClubNotFoundException("존재하지 않는 동아리입니다."));
        if(clubMemberRepository.existsClubMemberByUserIdAndClubId(user.getId(), club.getId())){
            throw new CustomException(ErrorCode.DUPLICATED_MEMBER);
        }


        LocalDateTime now = LocalDateTime.now();
        ClubMember clubMember = userClubMemberMapper.toEntity(clubRegisterRequest,club,user,now);
        clubMemberRepository.save(clubMember);
        return ClubRegisterResponse.builder().message("가입 신청 성공").club(club).build();
    }
    @Transactional
    @Override
    public ClubLeaveResponse leaveClubMember(ClubLeaveRequest clubLeaveRequest,String username) throws ClubNotFoundException,UserNotFoundException {
        User user = getUser(username);
        User target_user = userRepository.findUserById(clubLeaveRequest.userId()).orElseThrow(()->new UserNotFoundException("존재하지 않는 사용자입니다."));
        Long clubId = clubLeaveRequest.clubId();
        ClubMember clubMember = clubMemberRepository.findClubMemberByUserIdAndClubId(user.getId(),clubId).orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));;
        ClubProjection.ClubDetail club = clubRepository.findDetailById(clubLeaveRequest.clubId()).orElseThrow(() -> new ClubNotFoundException("존재하지 않는 동아리입니다."));
        if(clubMember.getStatus()!=MemberStatus.INACTIVE&&(Objects.equals(user.getId(), target_user.getId())||clubMember.getRole()==Role.LEADER||clubMember.getRole()==Role.VICE_LEADER)){
            ClubMember newUser = userClubMemberMapper.leaveClub(clubMember);
            clubMemberRepository.save(newUser);
            return ClubLeaveResponse.builder().message("동아리 탈퇴 성공").club(club).build();
        }
        else if(clubMember.getStatus()==MemberStatus.INACTIVE){
            //이미 탈퇴했는데 다시 요청한 경우
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
        else{
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
    }
    @Transactional
    public List<ClubSummary> getAllActiveClubs() {
        QClub qClub = QClub.club;

        List<ClubSummary> activeClubs = queryFactory
                .select(Projections.constructor(
                        ClubProjection.ClubSummary.class,
                        qClub.id,
                        qClub.name,
                        qClub.affiliation,
                        qClub.status,
                        Expressions.constant(new ArrayList<ClubMemberProjection.ClubMemberSummery>())
                ))
                .from(qClub)
                .where(qClub.status.eq(ClubStatus.ACTIVE))
                .fetch();

        return activeClubs;
    }

    public List<String> getAllMajors() {
        return Arrays.stream(Major.values())
                .map(Major::getLabel)
                .toList();
    }
}
