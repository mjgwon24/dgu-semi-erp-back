package com.example.dgu_semi_erp_back.repository.club;
import com.example.dgu_semi_erp_back.entity.club.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findClubIdById(Long id);
}
