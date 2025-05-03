package com.example.dgu_semi_erp_back.mapper;

import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.ClubRegisterRequest;
import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.ClubMemberDetail;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.*;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.entity.auth.user.UserRole;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.entity.club.ClubMember;
import com.example.dgu_semi_erp_back.entity.club.Role;
import org.mapstruct.*;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserClubMemberMapper {

    @Mapping(source = "user.nickname", target = "name")
    @Mapping(source = "user.major", target = "major")
    @Mapping(source = "user.studentNumber", target = "studentNumber")
    @Mapping(source = "registeredAt", target = "joinedAt")
    ClubMemberDetail toDto(ClubMember clubMember);

    @Mapping(source = "ClubRegisterRequestDto.status", target = "status", defaultValue = "HOLD")
    @Mapping(source = "ClubRegisterRequestDto.role",target = "role", defaultValue = "MEMBER")
    @Mapping(target = "id",ignore = true)
    ClubMember toEntity(ClubRegisterRequest ClubRegisterRequestDto,Club club,User user, LocalDateTime registeredAt);

    ClubMember toEntity(@MappingTarget ClubMember clubMember,UserRoleUpdateRequest request);


    @Mapping(target = "id", ignore = true)
    @Mapping(source = "role", target = "role")
    void updateUserRole(@MappingTarget ClubMember clubMember, UserRoleUpdateRequest request);
}