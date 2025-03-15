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

public class Club {
    @Id
    @JoinColumn(name = "clubId", nullable = false)
    private int clubId;
    private String clubName;
    private String affiliation;
    private LocalDateTime createdAt;
}
