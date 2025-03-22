package com.example.dgu_semi_erp_back.entity.User;

import com.example.dgu_semi_erp_back.common.support.BaseEntity;
import com.example.dgu_semi_erp_back.entity.Account.Account;
import com.example.dgu_semi_erp_back.entity.Club.ClubMember;
import com.example.dgu_semi_erp_back.entity.Club.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(nullable = false)
    private Role role; // 역할 추가

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean isVerified; // 이메일 인증 여부

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Account> accounts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClubMember> clubMembers;
}
