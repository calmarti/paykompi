package com.calmarti.paykompi.user.service;

import com.calmarti.paykompi.user.dto.CreateUserRequestDto;
import com.calmarti.paykompi.user.dto.UpdateUserRequestDto;
import com.calmarti.paykompi.user.dto.UserResponseDto;

import java.util.UUID;


public interface UserService {
    UserResponseDto createUser(CreateUserRequestDto request);
    UserResponseDto getUserById(UUID id);
    void updateUserById(UUID id, UpdateUserRequestDto request);
//    void changeUserStatus(UUID id, UserStatus status);
//    UserResponseDto closeUserAcount(UUID id);
//    void changeUserPassword(UUID id, String password);
}
