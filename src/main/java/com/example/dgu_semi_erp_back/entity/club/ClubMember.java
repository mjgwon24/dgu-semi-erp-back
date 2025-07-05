package com.example.dgu_semi_erp_back.entity.club;

import com.example.dgu_semi_erp_back.entity.auth.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
public class ClubMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'HOLD'")
    private MemberStatus status = MemberStatus.ACTIVE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'MEMBER'")
    private Role role = Role.MEMBER;

    @Column(nullable = false)
    private LocalDateTime registeredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    public void changeStatus(MemberStatus status) {
        this.status = status;
    }

    public ClubMember(User user, Club club, Role role, MemberStatus status) {
        this.user = user;
        this.club = club;
        this.role = role;
        this.status = status;
        this.registeredAt = LocalDateTime.now();
    }

}
