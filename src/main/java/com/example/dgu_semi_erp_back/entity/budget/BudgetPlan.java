package com.example.dgu_semi_erp_back.entity.budget;

import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanUpdateRequest;
import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.entity.budget.types.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
public class BudgetPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_plan_id")
    private Long id;

    @Column(nullable = false)
    private String executeType; // 집행 유형

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
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
    private String planReviewer; // 검토자

    @Column(nullable = false)
    private String planApprover; // 승인자

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BudgetStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder(
            builderClassName = "UpdateBudgetPlanBuilder",
            builderMethodName = "prepareUpdate",
            buildMethodName = "update"
    )
    public void update(BudgetPlanUpdateRequest request, LocalDateTime updatedAt) {
        this.executeType = request.executeType();
        this.paymentType = request.paymentType();
        this.paymentDate = request.paymentDate();
        this.content = request.content();
        this.author = request.author();
        this.paymentAmount = request.paymentAmount();
        this.planReviewer = request.planReviewer();
        this.planApprover = request.planApprover();
        this.status = request.status();
        this.updatedAt = updatedAt;
    }
}