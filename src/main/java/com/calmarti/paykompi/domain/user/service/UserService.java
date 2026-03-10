package com.calmarti.paykompi.domain.user.service;

import com.calmarti.paykompi.common.dto.CustomPage;
import com.calmarti.paykompi.domain.user.dto.CreateUserRequestDto;
import com.calmarti.paykompi.domain.user.dto.UpdateUserRequestDto;
import com.calmarti.paykompi.domain.user.dto.UpdateUserStatusDto;
import com.calmarti.paykompi.domain.user.dto.UserResponseDto;
import com.calmarti.paykompi.domain.user.entity.User;
import com.calmarti.paykompi.domain.user.enums.UserStatus;
import com.calmarti.paykompi.domain.user.enums.UserType;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface UserService {
    UserResponseDto createUser(CreateUserRequestDto dto);
    UserResponseDto getUserById(UUID id, User authenticatedUser);
    void updateUserById(UUID id, UpdateUserRequestDto dto, User authenticatedUser);
    void changeUserStatus(UUID id, UpdateUserStatusDto dto);  // -> requires role = ADMIN
    void deleteUser(UUID id, User authenticatedUser);
    CustomPage<UserResponseDto> getAllUsers(UserType userType, UserStatus userStatus,Pageable pageable);
}
