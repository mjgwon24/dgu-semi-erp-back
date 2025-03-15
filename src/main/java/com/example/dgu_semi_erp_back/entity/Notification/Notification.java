package com.example.dgu_semi_erp_back.entity.Notification;

import com.example.dgu_semi_erp_back.entity.Club.Club;
import com.example.dgu_semi_erp_back.entity.User.User;
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

public class Notification {
    @Id
    private int notificationId;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;
    private String content;

    private String category;

    private LocalDateTime date;

    private Boolean read;
}
