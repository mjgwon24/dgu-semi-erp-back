package com.example.dgu_semi_erp_back.entity.Budget;

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
public class BudgetPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_plan_id")
    private int id;

    @Column(nullable = false)
    private String executeType; // 집행 유형

    @Column(nullable = false)
    private PaymentType paymentType; // 결제 타입

    @Column(nullable = false)
    private LocalDateTime paymentDate; // 결제일

    @Column(nullable = false)
    private String content; // 내용

    @Column(nullable = false)
    private String owner; // 기안자

    @Column(nullable = false)
    private int amount; // 금액

    @Column(nullable = false)
    private BudgetStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}