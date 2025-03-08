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
public class BudgetReport {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int budgetReportId;
    @JoinColumn(name = "budgetUsageId")
    private int budgetUsageId;

    @Column(nullable = false)
    private String executeType;

    private int clubId;
    private String content;
    private int ownerId;
    private LocalDateTime createAt;
    private int usedAmount;
}
