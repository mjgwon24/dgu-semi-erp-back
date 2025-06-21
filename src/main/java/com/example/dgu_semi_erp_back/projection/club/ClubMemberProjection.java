package com.example.dgu_semi_erp_back.projection.club;

import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.entity.club.*;
import com.example.dgu_semi_erp_back.entity.club.ClubMember;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

public class ClubMemberProjection {
    public record ClubMemberSummery(
            Long userId,         // qUser.id
            Long id,             // qMember.id
            String name,         // qUser.username
            String major,        // qUser.major
            Integer studentNumber, // qUser.studentNumber
            Role role,           // qMember.role
            MemberStatus status, // qMember.status
            LocalDateTime registeredAt // qMember.registeredAt
    ) {}
}
