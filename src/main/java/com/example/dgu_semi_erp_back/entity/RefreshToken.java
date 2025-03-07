package com.example.dgu_semi_erp_back.entity;

import com.example.dgu_semi_erp_back.auth.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RefreshToken {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void updateToken(String token) {
        this.token = token;
    }
}
