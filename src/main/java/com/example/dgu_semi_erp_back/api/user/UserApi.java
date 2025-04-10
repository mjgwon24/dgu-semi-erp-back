package com.example.dgu_semi_erp_back.api.user;

import com.example.dgu_semi_erp_back.dto.user.UserResponse;
import com.example.dgu_semi_erp_back.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse getUser(@CookieValue(name = "accessToken", required = true) String accessToken,@CookieValue(name = "refreshToken", required = true) String refreshToken){
        UserResponse user = userService.getUserByToken(accessToken);
        return user;
    }
}
