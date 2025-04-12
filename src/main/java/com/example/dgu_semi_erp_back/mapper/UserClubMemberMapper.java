package com.example.dgu_semi_erp_back.mapper;

import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.ClubRegisterRequest;
import com.example.dgu_semi_erp_back.dto.club.UserClubMemberDto.ClubMemberDetail;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.entity.club.ClubMember;
import com.example.dgu_semi_erp_back.entity.club.Role;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserClubMemberMapper {

    @Mapping(source = "user.nickname", target = "name")
    @Mapping(source = "user.major", target = "major")
    @Mapping(source = "user.studentNumber", target = "studentNumber")
    @Mapping(source = "registeredAt", target = "joinedAt")
    ClubMemberDetail toDto(ClubMember clubMember);

    @Mapping(target = "role", defaultValue = "MEMBER")
    @Mapping(target = "status", defaultValue = "ACTIVE")
    @Mapping(target = "club", ignore = true)
    @Mapping(target = "user", ignore = true)
    ClubMember toEntity(ClubRegisterRequest ClubRegisterRequestDto,Long clubId,Long userId, LocalDateTime registeredAt);
    @AfterMapping
    default void assignEntities(@MappingTarget ClubMember member, Long clubId, Long userId) {
        member.setClub(Club.builder().id(clubId).build());
        member.setUser(User.builder().id(userId).build());
    }
}