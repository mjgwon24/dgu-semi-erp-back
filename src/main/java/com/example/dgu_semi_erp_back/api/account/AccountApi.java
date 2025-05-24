package com.example.dgu_semi_erp_back.api.account;

import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountUpdateRequest;
import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountInfoResponse;
import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountInfoDetailResponse;
import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountCreateRequest;
import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountCreateResponse;
import com.example.dgu_semi_erp_back.dto.account.AccountHistoryCommandDto.AccountHistoryDetailResponse;
import com.example.dgu_semi_erp_back.dto.common.PaginationInfo;
import com.example.dgu_semi_erp_back.exception.AccountNotFoundException;
import com.example.dgu_semi_erp_back.exception.ClubNotFoundException;
import com.example.dgu_semi_erp_back.exception.UserNotFoundException;
import com.example.dgu_semi_erp_back.mapper.AccountMapper;
import com.example.dgu_semi_erp_back.service.notification.NotificationService;
import com.example.dgu_semi_erp_back.usecase.account.AccountUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * 통장관리 API
 * @author 권민지
 * @since 2025.04.05
 * @LastModified 2025.04.19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Validated
public class AccountApi {
    private final AccountUseCase accountUseCase;
    private final AccountMapper accountMapper;
    private final NotificationService notificationService;

    /**
     * 통장 개설
     * @param request 통장 번호, 개설일, 동아리 ID, 소유주 ID
     * @return Account
     */
    @PostMapping("/protected")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountCreateResponse createAccount(
            @RequestBody @Valid AccountCreateRequest request,
            HttpServletRequest httpRequest
    ){
        String username = (String) httpRequest.getAttribute("username");

        var account = accountUseCase.createAccount(request, username);

        return accountMapper.toAccountCreateResponse(account);
    }

    /**
     * 통장 정보 조회
     * @param clubId 동아리 ID
     * @return AccountInfoResponse 통장 번호, 개설일, 소유주 이름, 동아리 이름, (List)통장 거래 내역, PaginationInfo
     */
    @GetMapping("/{clubId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountInfoDetailResponse getAccountWithHistories(
            @PathVariable("clubId") Long clubId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "7") int size
            ) {
        try {
            var account = accountUseCase.getAccountByClubId(clubId);
            var pagedHistories = accountUseCase.getPagedAccountHistories(account.getId(), page, size);

            return AccountInfoDetailResponse.builder()
                    .number(account.getNumber())
                    .createdAt(account.getCreatedAt())
                    .ownerName(account.getUser().getUsername())
                    .clubName(account.getClub().getName())
                    .accountHistories(account.getAccountHistories().stream()
                            .map(history -> new AccountHistoryDetailResponse(
                                    history.getPayType(),
                                    history.getContent(),
                                    history.getUsedAmount(),
                                    history.getTotalAmount(),
                                    history.getCreatedAt()
                            ))
                            .toList())
                    .paginationInfo(new PaginationInfo(
                            pagedHistories.getNumber(),
                            pagedHistories.getSize(),
                            pagedHistories.getTotalPages(),
                            pagedHistories.getTotalElements()
                    ))
                    .build();
        } catch (ClubNotFoundException | AccountNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * 통장 정보 변경
     * @param accountId 통장 ID
     * @param request 통장 번호, 개설일, 동아리 이름, 소유주 이름
     * @return Account
     */
    @PutMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountInfoResponse updateAccount(
            @PathVariable("accountId") Long accountId,
            @RequestBody @Valid AccountUpdateRequest request
    ) {
        try {
            var account = accountUseCase.updateAccount(accountId, request);

            notificationService.send(
                account.getUser(),
                com.example.dgu_semi_erp_back.entity.notification.Category.BANKBOOK,
                "통장 정보가 수정되었습니다.",
                "계좌번호: " + account.getNumber()
            );

            return AccountInfoResponse.builder()
                    .number(account.getNumber())
                    .createdAt(account.getCreatedAt())
                    .updatedAt(account.getUpdatedAt())
                    .ownerName(account.getUser().getUsername())
                    .clubName(account.getClub().getName())
                    .build();
        } catch (AccountNotFoundException | UserNotFoundException | ClubNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * 동아리 별 통장 소프트 삭제
     * @param accountId 통장 ID
     * @return ResponseStatus, message
     */
    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteAccount(
            @PathVariable("accountId") Long accountId
    ) {
        try {
            var account = accountUseCase.getAccountByClubId(accountId);
            notificationService.send(
                account.getUser(),
                com.example.dgu_semi_erp_back.entity.notification.Category.BANKBOOK,
                "통장이 삭제되었습니다.",
                "계좌번호: " + account.getNumber()
            );

            accountUseCase.deleteAccount(accountId);
            return ResponseEntity.ok("통장이 정상적으로 삭제되었습니다.");
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
