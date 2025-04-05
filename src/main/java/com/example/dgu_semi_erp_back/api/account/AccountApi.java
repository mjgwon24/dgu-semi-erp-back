package com.example.dgu_semi_erp_back.api.account;

import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountCreateRequest;
import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountCreateResponse;
import com.example.dgu_semi_erp_back.exception.ClubNotFoundException;
import com.example.dgu_semi_erp_back.exception.UserNotFoundException;
import com.example.dgu_semi_erp_back.usecase.account.AccountCreateUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Validated
public class AccountApi {
    private final AccountCreateUseCase accountCreateUseCase;

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
}
