package com.example.dgu_semi_erp_back.repository.club;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.projection.club.ClubProjection.ClubSummery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findClubIdById(Long id);
    Optional<ClubSummery> findClubById(Long id);
    Optional<Club> findByName(String name);
}
