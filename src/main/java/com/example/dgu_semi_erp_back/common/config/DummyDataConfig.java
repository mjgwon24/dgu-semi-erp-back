package com.example.dgu_semi_erp_back.common.config;

import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.entity.club.MemberStatus;
import com.example.dgu_semi_erp_back.repository.club.ClubRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DummyDataConfig {

    @Bean
    CommandLineRunner initClubData(ClubRepository clubRepository) {
        return args -> {
            Club club = Club.builder()
                    .name("디벨로퍼")
                    .affiliation("공대")
                    .status(MemberStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            clubRepository.save(club);
        };
    }

}