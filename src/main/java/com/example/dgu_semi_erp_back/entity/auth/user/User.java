package com.example.dgu_semi_erp_back.entity.auth.user;

import com.example.dgu_semi_erp_back.common.support.BaseEntity;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.UserUpdateRequest;
import com.example.dgu_semi_erp_back.entity.account.Account;
import com.example.dgu_semi_erp_back.entity.club.ClubMember;
import com.example.dgu_semi_erp_back.entity.club.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private Integer studentNumber;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean isVerified; // 이메일 인증 여부

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Account> accounts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClubMember> clubMembers;

    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }
}
