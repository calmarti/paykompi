package com.calmarti.paykompi.user.service;

import com.calmarti.paykompi.user.dto.CreateUserRequestDto;
import com.calmarti.paykompi.user.dto.UpdateUserRequestDto;
import com.calmarti.paykompi.user.dto.UpdateUserStatusDto;
import com.calmarti.paykompi.user.dto.UserResponseDto;

import java.util.UUID;


public interface UserService {
    UserResponseDto createUser(CreateUserRequestDto dto);
    UserResponseDto getUserById(UUID id);
    void updateUserById(UUID id, UpdateUserRequestDto dto);
    void changeUserStatus(UUID id, UpdateUserStatusDto dto);
    //void changeUserPassword(UUID id, String password);
}
