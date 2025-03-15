package com.example.dgu_semi_erp_back.entity.Bankbook;
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
public class BankbookHistory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int bankbookHistoryId;

    @JoinColumn(name = "bankbookId")
    private int bankbookId;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(nullable = false)
    private Role role; // 역할 추가

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private int usedAmount;


}