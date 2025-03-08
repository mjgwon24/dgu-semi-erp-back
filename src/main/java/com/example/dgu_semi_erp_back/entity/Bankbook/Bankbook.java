package com.example.dgu_semi_erp_back.entity.Bankbook;

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
public class Bankbook {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int bankbookId;

    @JoinColumn(name = "clubId")
    private int clubId;

    //통장 소유자
    @Column(nullable = false)
    private int owner;

    //계좌번호
    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private LocalDateTime createAt;



}