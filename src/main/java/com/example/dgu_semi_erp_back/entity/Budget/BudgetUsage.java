package com.example.dgu_semi_erp_back.entity.Budget;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class BudgetUsage {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int budgetUsageId;

    @JoinColumn(name = "budgetPlanId")
    private int budgetPlanId;

    @Column(nullable = false)
    private String executeType;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int owner;

    @Column(nullable = false)
    private PaymentType paymentType;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private BudgetStatus status;

    @Column(nullable = false)
    private String file;
}
