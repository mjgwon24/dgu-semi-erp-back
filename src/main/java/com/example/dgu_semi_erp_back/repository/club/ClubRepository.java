package com.example.dgu_semi_erp_back.repository.club;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.projection.club.ClubProjection;
import com.example.dgu_semi_erp_back.projection.club.ClubProjection.ClubSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findClubIdById(Long id);
    Optional<ClubSummary> findClubById(Long id);
    Optional<ClubProjection.ClubDetail> findDetailByName(String name);
    Optional<ClubProjection.ClubDetail> findDetailById(Long Id);
    Optional<Club> findByName(String name);
}
