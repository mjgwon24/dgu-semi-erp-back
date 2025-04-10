package com.example.dgu_semi_erp_back.service.user;

import com.example.dgu_semi_erp_back.common.jwt.JwtUtil;
import com.example.dgu_semi_erp_back.dto.user.UserResponse;
import com.example.dgu_semi_erp_back.entity.club.Club;
import com.example.dgu_semi_erp_back.entity.auth.user.User;
import com.example.dgu_semi_erp_back.repository.club.ClubRepository;
import com.example.dgu_semi_erp_back.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private final JwtUtil jwtutil;
    public UserResponse getUserByToken(String accessToken) throws NoSuchElementException{
        String userName = jwtutil.getUsernameFromToken(accessToken);
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
        Club clubID = clubRepository.findClubIdById(user.getId());
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .clubId(clubID.getId())
                .build();
    }
}
