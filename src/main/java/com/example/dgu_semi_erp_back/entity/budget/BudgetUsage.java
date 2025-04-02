package com.example.dgu_semi_erp_back.entity.budget;

import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.entity.budget.types.PaymentType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class BudgetUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_usage_id")
    private Long id;
    @Column(nullable = false)
    private String executeType; // 집행 유형

    @Column(nullable = false)
    private PaymentType paymentType; // 결제 타입

    @Column(nullable = false)
    private LocalDateTime paymentDate; // 결제일

    @Column(nullable = false)
    private String content; // 내용

    @Column(nullable = false)
    private String author; // 기안자

    @Column(nullable = false)
    private int paymentAmount; // 금액

    @Column(nullable = false)
    private String usageReviewer; // 검토자

    @Column(nullable = false)
    private String usageApprover; // 승인자

    @Column(nullable = false)
    private BudgetStatus status;

    @Column(nullable = false)
    private String file;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
