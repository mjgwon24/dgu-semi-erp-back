package com.example.dgu_semi_erp_back.entity.budget;

import com.example.dgu_semi_erp_back.dto.budget.BudgetPlanCommandDto.BudgetPlanUpdateRequest;
import com.example.dgu_semi_erp_back.entity.budget.types.BudgetStatus;
import com.example.dgu_semi_erp_back.entity.budget.types.PaymentType;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
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

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = true)
    @JsonIgnore
    private Club club; // 동아리

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType; // 결제 타입

    @Column(nullable = false)
    private String content; // 내용

    @Column(nullable = false)
    private String author; // 기안자

    @Column(nullable = false)
    private LocalDateTime expectedPaymentDate; // 결제 예정일

    @Column(nullable = false)
    private int paymentAmount; // 금액

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BudgetStatus status; // 상태

    @Column(nullable = false)
    private String planReviewer; // 검토자

    @Column(nullable = false)
    private String planApprover; // 승인자

    @Column(nullable = false)
    private LocalDateTime createdAt; // 기안일

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 수정날짜

    @Builder(
            builderClassName = "UpdateBudgetPlanBuilder",
            builderMethodName = "prepareUpdate",
            buildMethodName = "update"
    )
    public void update(BudgetPlanUpdateRequest request, LocalDateTime updatedAt) {
        this.executeType = request.executeType();
        this.paymentType = request.paymentType();
        this.content = request.content();
        this.author = request.author();
        this.expectedPaymentDate = request.expectedPaymentDate();
        this.status = BudgetStatus.HOLD;
        this.paymentAmount = request.paymentAmount();
        this.planReviewer = request.planReviewer();
        this.planApprover = request.planApprover();
        this.updatedAt = updatedAt;
    }

    public void updateStatus(BudgetStatus status) {
        this.status = status;
    }
}