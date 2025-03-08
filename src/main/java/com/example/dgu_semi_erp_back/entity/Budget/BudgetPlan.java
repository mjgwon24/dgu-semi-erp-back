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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int budgetPlanId;

    @JoinColumn(name = "clubId")
    private int clubId;

    @Column(nullable = false)
    private String executeType;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int owner;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private BudgetStatus status;



}