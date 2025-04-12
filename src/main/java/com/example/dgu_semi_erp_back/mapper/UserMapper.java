package com.example.dgu_semi_erp_back.mapper;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.*;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import org.mapstruct.*;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
@Mapper(
        componentModel = SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "role", target = "role")
    void updateUserRole(@MappingTarget User user, UserRoleUpdateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "email", target = "email")
    void updateUserEmail(@MappingTarget User user, UserEmailUpdateRequest request);
}
