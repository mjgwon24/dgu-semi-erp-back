package com.example.dgu_semi_erp_back.service.account;

import com.example.dgu_semi_erp_back.common.exception.CustomException;
import com.example.dgu_semi_erp_back.common.exception.ErrorCode;
import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountCreateRequest;
import com.example.dgu_semi_erp_back.entity.account.Account;
import com.example.dgu_semi_erp_back.entity.account.AccountHistory;
import com.example.dgu_semi_erp_back.entity.account.QAccount;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.entity.club.ClubMember;
import com.example.dgu_semi_erp_back.entity.club.MemberStatus;
import com.example.dgu_semi_erp_back.entity.club.Role;
import com.example.dgu_semi_erp_back.exception.AccountNotFoundException;
import com.example.dgu_semi_erp_back.exception.ClubNotFoundException;
import com.example.dgu_semi_erp_back.exception.UserNotFoundException;
import com.example.dgu_semi_erp_back.mapper.AccountMapper;
import com.example.dgu_semi_erp_back.repository.account.AccountCommandRepository;
import com.example.dgu_semi_erp_back.repository.account.AccountHistoryQueryRepository;
import com.example.dgu_semi_erp_back.repository.account.AccountQueryRepository;
import com.example.dgu_semi_erp_back.repository.auth.UserRepository;
import com.example.dgu_semi_erp_back.repository.club.ClubMemberRepository;
import com.example.dgu_semi_erp_back.repository.club.ClubQueryRepository;
import com.example.dgu_semi_erp_back.repository.club.ClubRepository;
import com.example.dgu_semi_erp_back.service.notification.NotificationService;
import com.example.dgu_semi_erp_back.entity.notification.Category;
import com.example.dgu_semi_erp_back.usecase.account.AccountUseCase;
import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountUpdateRequest;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountCommandService implements AccountUseCase {
    private final AccountCommandRepository accountCommandRepository;
    private final AccountQueryRepository accountQueryRepository;
    private final AccountHistoryQueryRepository accountHistoryQueryRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private final ClubQueryRepository clubQueryRepository;
    private final EntityManager entityManager;
    private final AccountMapper accountMapper;
    private final ClubMemberRepository clubMemberRepository;
    private final NotificationService notificationService;

    @Override
    public Account createAccount(AccountCreateRequest request, String username) {
        if (username == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        Instant now = Instant.now();

        // 동아리 존재 여부 검증
        Club club = clubRepository.findById(request.clubId())
                .orElseThrow(() -> new ClubNotFoundException("해당 동아리가 존재하지 않습니다."));

        // 통장 소유자 존재 여부 검증
        User owner = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException("해당 사용자가 존재하지 않습니다."));

        // API 요청자 존재 여부 검증
        User requester = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("요청 사용자가 존재하지 않습니다."));

        // 요청자 권한 검증
        ClubMember requesterMember = clubMemberRepository.findByClubIdAndUserId(club.getId(), requester.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_REQUEST));
        if (requesterMember.getStatus() != MemberStatus.ACTIVE) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        }

        // 요청자가 MEMBER보다 높은 권한인지 검증 (TREASURER 이상)
        if (requesterMember.getRole().getPriority() <= Role.MEMBER.getPriority()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
        }

        // 통장 소유자 권한 검증
        ClubMember ownerMember = clubMemberRepository.findByClubIdAndUserId(club.getId(), owner.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_CLUB_MEMBER));

        // 소유자의 Role이 VICE_LEADER(3) 이상인지 검증
        if (ownerMember.getRole().getPriority() <= Role.TREASURER.getPriority()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        // 통장 생성
        Account account = accountMapper.toEntity(request, owner, club, now);
        Account savedAccount = accountCommandRepository.save(account);

        try {
            notificationService.send(
                    owner,
                    Category.BANKBOOK,
                    "통장이 추가되었습니다",
                    club.getName() + " 동아리의 통장이 추가되었습니다.\n계좌번호: " + savedAccount.getNumber()
            );
        } catch (Exception e) {
            log.error("알림 전송 실패", e);
        }

        return savedAccount;
    }

    @Override
    public Account getAccountByClubId(Long clubId) {
        return accountQueryRepository.findAccountByClubId(clubId)
                .orElseThrow(() -> new AccountNotFoundException("해당 통장이 존재하지 않습니다."));
    }

    @Override
    public Page<AccountHistory> getPagedAccountHistories(Long accountId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return accountHistoryQueryRepository.findPagedHistoriesByAccountId(accountId, pageable);
    }

    @Transactional
    @Override
    public Account updateAccount(
            Long accountId,
            AccountUpdateRequest request
    ) {
        Account account = accountQueryRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("해당 통장이 존재하지 않습니다."));

        Club club = clubRepository.findById(request.clubId())
                .orElseThrow(() -> new ClubNotFoundException("해당 동아리가 존재하지 않습니다."));

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException("해당 사용자가 존재하지 않습니다."));

        QAccount qAccount = QAccount.account;

        long updatedCount = new JPAUpdateClause(entityManager, qAccount)
                .where(qAccount.id.eq(accountId))
                .set(qAccount.number, request.number())
                .set(qAccount.club.id, club.getId())
                .set(qAccount.user.id, user.getId())
                .set(qAccount.updatedAt, Instant.now())
                .execute();

        if (updatedCount == 0) {
            throw new AccountNotFoundException("해당 통장이 존재하지 않습니다.");
        }

        Account updatedAccount = accountQueryRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("해당 통장이 존재하지 않습니다."));

        notificationService.send(
            user,
            Category.BANKBOOK,
            "통장이 수정되었습니다",
            club.getName() + " 동아리의 통장이 수정되었습니다."
        );

        return updatedAccount;
    }

    /**
     * 통장 삭제
     * - 비회원(동아리 미가입자)는 삭제 불가
     * - 삭제 요청자의 소속 동아리에 속한 통장이어야만 삭제 가능
     * - 동아리 내에서 관리자 또는 회계 등 높은 권한을 가진 사용자만 삭제 가능
     * @param accountId 통장 ID
     */
    @Transactional
    @Override
    public void deleteAccount(Long accountId) {
        Account account = accountQueryRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("해당 통장이 존재하지 않습니다."));

        // 요청자 정보 반환 -> 추가 필요
        // User currentUser = getCurrentUser();
        // 현재는 임시 구현
        User currentUser = userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundException("해당 사용자가 존재하지 않습니다."));

        // 비회원(동아리 미가입자) 확인
        if (currentUser.getClubMembers() == null || currentUser.getClubMembers().isEmpty()) {
        }

        // 요청자의 소속 동아리에 속한 통장인지 확인
        long clubMemberId = currentUser.getClubMembers().stream()
                .filter(clubMember -> clubMember.getClub().getId().equals(account.getClub().getId()))
                .map(ClubMember::getId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("해당 동아리의 통장이 아닙니다."));

        // 요청자 권한 확인 - 해당 clubMemberId를 가진 ClubMember의 Role이 MEMBER보다 높은지 확인
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId)
                .orElseThrow(() -> new UserNotFoundException("해당 동아리의 통장이 아닙니다."));

        if (clubMember.getRole().getPriority() <= Role.MEMBER.getPriority()) {
            throw new UserNotFoundException("권한이 부족합니다.");
        }

        // soft delete
        account.markAsDeleted(Instant.now());
        accountCommandRepository.save(account);

        notificationService.send(
            account.getUser(),
            Category.BANKBOOK,
            "통장이 삭제되었습니다",
            account.getClub().getName() + " 동아리의 통장이 삭제되었습니다."
        );
    }

    /**
     * 통장을 보유한 동아리 목록 조회
     * - account 정보를 보유한 동아리 목록을 페이지 단위로 조회
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지 크기
     */
    @Override
    public Page<Club> getPagedAccounts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clubQueryRepository.getPagedAccounts(pageable);
    }
}
