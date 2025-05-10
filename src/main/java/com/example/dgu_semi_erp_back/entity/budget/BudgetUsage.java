package com.example.dgu_semi_erp_back.entity.budget;

import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.entity.budget.types.PaymentType;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
public class BudgetUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_usage_id")
    private Long id;
    @Column(nullable = false)
    private String executeType; // 집행 유형

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = true)
    @JsonIgnore
    private Club club; // 동아리

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType; // 결제 타입

    @Column(nullable = false)
    private LocalDateTime paymentDate; // 결제일

    @Column(nullable = false)
    private String content; // 내용

    @Column(nullable = false)
    private String drafter; // 기안자

    @Column(nullable = false)
    private int paymentAmount; // 금액

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BudgetStatus status; // 상태

    @Column(nullable = false)
    private String usageReviewer; // 검토자

    @Column(nullable = false)
    private String usageApprover; // 승인자

    @Column(nullable = true)
    private String file; // 첨부 파일
    
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
