package com.example.dgu_semi_erp_back.mapper;

import com.example.dgu_semi_erp_back.dto.account.AccountCommandDto.AccountCreateRequest;
import com.example.dgu_semi_erp_back.entity.account.Account;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.entity.club.Club;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.Instant;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountDtoMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", source = "dto.createdAt"),
            @Mapping(target = "user", source = "user"),
            @Mapping(target = "club", source = "club"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
    })
    Account toEntity(
            AccountCreateRequest dto,
            User user,
            Club club,
            Instant updatedAt
    );
}
