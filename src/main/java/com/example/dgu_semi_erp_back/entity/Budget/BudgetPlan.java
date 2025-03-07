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
    private int budget_plan_id;

    @JoinColumn(name = "club_id")
    private int club_id;

    @Column(nullable = false)
    private ExecuteType Execute_type;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int owner;

    @Column(nullable = false)
    private LocalDateTime create_at;

    @Column(nullable = false)
    private LocalDateTime payment_date;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private BudgetStatus Status;



}