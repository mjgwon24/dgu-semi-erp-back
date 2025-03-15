package com.example.dgu_semi_erp_back.entity.Club;
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
public class ClubPlan {
    @Id
    @JoinColumn(name = "clubPlanId", nullable = false)
    private int clubPlanId;

    @Column(nullable = false)
    private int clubId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private Repeat repeat;
}
