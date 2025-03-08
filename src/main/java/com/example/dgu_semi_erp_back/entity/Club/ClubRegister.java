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
public class ClubRegister {
    @Id
    @JoinColumn(name = "clubRegisterId", nullable = false)
    private int clubRegisterId;

    @Column(nullable = false)
    private int clubId;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime registerAt;

    @Column(nullable = false)
    private Role role;

}
