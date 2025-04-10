package com.example.dgu_semi_erp_back.mapper;

import com.example.dgu_semi_erp_back.dto.peoplemanagement.UserClubMemberDto.ClubMemberDetail;
import com.example.dgu_semi_erp_back.entity.club.ClubMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserClubMemberMapper {

    @Mapping(source = "user.nickname", target = "name")
    @Mapping(source = "user.major", target = "major")
    @Mapping(source = "user.studentNumber", target = "studentNumber")
    @Mapping(source = "registeredAt", target = "joinedAt")
    ClubMemberDetail toDto(ClubMember clubMember);
}