package com.example.dgu_semi_erp_back.usecase.user;

import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.UserRoleUpdateRequest;
import com.example.dgu_semi_erp_back.dto.user.UserCommandDto.UserEmailUpdateRequest;
import com.example.dgu_semi_erp_back.entity.auth.user.User;

public interface UserUpdateUseCase {
    User updateRole(Long id,UserRoleUpdateRequest request,String accessToken,String refreshToken);
    User updateEmail(Long id, UserEmailUpdateRequest request, String accessToken, String refreshToken);
}
