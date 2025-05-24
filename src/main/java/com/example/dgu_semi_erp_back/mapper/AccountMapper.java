package com.example.dgu_semi_erp_back.mapper;

import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountCreateResponse;
import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountCreateRequest;
import com.example.dgu_semi_erp_back.entity.account.Account;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.entity.club.Club;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", source = "dto.createdAt")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "club", source = "club")
    @Mapping(target = "updatedAt", source = "updatedAt")
    Account toEntity(
            AccountCreateRequest dto,
            User user,
            Club club,
            Instant updatedAt
    );

    @Mapping(source = "id", target = "accountId")
    @Mapping(source = "number", target = "accountNumber")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "club", target = "club")
    AccountCreateResponse toAccountCreateResponse(Account account);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "email", target = "userEmail")
    AccountCreateResponse.UserInfo toUserInfo(User user);

    @Mapping(source = "id", target = "clubId")
    @Mapping(source = "name", target = "clubName")
    AccountCreateResponse.ClubInfo toClubInfo(Club club);
}
