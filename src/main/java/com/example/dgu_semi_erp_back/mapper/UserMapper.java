package com.example.dgu_semi_erp_back.mapper;
import com.example.dgu_semi_erp_back.dto.auth.SignUpRequest;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.*;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.entity.auth.user.UserRole;
import org.mapstruct.*;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(
        componentModel = SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    // 이메일 변경용
    default User toEntity(@MappingTarget User user, UserEmailUpdateRequest request) {
        user.changeEmail(request.email());
        return user;
    }

    default User toEntity(SignUpRequest request) {
        return User.builder()
                .email(request.email())
                .password(request.password())
                .role(UserRole.USER)
                .build();
    }
}