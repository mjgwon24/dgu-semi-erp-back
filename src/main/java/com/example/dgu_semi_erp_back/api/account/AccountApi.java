package com.example.dgu_semi_erp_back.api.account;

import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountInfoResponse;
import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountCreateRequest;
import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountCreateResponse;
import com.example.dgu_semi_erp_back.dto.account.AccountHistoryCommandDto.AccountHistoryDetailResponse;
import com.example.dgu_semi_erp_back.dto.common.PaginationInfo;
import com.example.dgu_semi_erp_back.exception.AccountNotFoundException;
import com.example.dgu_semi_erp_back.exception.ClubNotFoundException;
import com.example.dgu_semi_erp_back.exception.UserNotFoundException;
import com.example.dgu_semi_erp_back.usecase.account.AccountCreateUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * 통장관리 API
 * @author 권민지
 * @since 2025.04.05
 * @LastModified 2025.04.11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Validated
public class AccountApi {
    private final AccountCreateUseCase accountCreateUseCase;

    /**
     * 통장 개설
     * @param request 통장 번호, 개설일, 동아리 ID, 소유주 ID
     * @return Account
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountCreateResponse create(
            @RequestBody @Valid AccountCreateRequest request
    ){
        try {
            var account = accountCreateUseCase.create(request);

            return AccountCreateResponse.builder()
                    .account(account)
                    .build();
        } catch (ClubNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * 통장 정보 조회
     * @param clubId 동아리 ID
     * @return AccountInfoResponse 통장 번호, 개설일, 소유주 이름, 동아리 이름, (List)통장 거래 내역
     */
    @GetMapping("/{clubId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountInfoResponse getAccountWithHistories(
            @PathVariable("clubId") Long clubId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "7") int size
            ) {
        try {
            var account = accountCreateUseCase.getAccountByClubId(clubId);
            var pagedHistories = accountCreateUseCase.getPagedAccountHistories(account.getId(), page, size);

            return AccountInfoResponse.builder()
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
}
