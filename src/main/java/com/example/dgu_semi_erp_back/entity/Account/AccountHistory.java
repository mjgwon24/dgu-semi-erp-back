package com.example.dgu_semi_erp_back.entity.Account;
import com.example.dgu_semi_erp_back.entity.Club.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_history_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // 역할 추가

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int totalAmount; // 계좌 잔여 금액

    @Column(nullable = false)
    private int usedAmount; // 사용 금액

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}