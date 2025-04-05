package com.example.dgu_semi_erp_back.repository.club;
import com.example.dgu_semi_erp_back.entity.club.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Club findClubIdById(Long id);
}
