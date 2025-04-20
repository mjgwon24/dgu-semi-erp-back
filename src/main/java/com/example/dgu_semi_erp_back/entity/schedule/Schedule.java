package com.example.dgu_semi_erp_back.entity.schedule;

import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.entity.schedule.type.ScheduleRepeat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "place", nullable = false)
    private String place;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_repeat", nullable = true)
    private ScheduleRepeat repeat;

    public void update(String title, LocalDateTime date, String place, ScheduleRepeat repeat) {
        this.title = title;
        this.date = date;
        this.place = place;
        this.repeat = repeat;
    }


}
