package com.calmarti.paykompi.user.service;

import com.calmarti.paykompi.user.dto.CreateUserRequestDto;
import com.calmarti.paykompi.user.dto.UpdateUserRequestDto;
import com.calmarti.paykompi.user.dto.UpdateUserStatusDto;
import com.calmarti.paykompi.user.dto.UserResponseDto;

import java.util.UUID;


public interface UserService {
    //TODO: Include userRole as optional request field (default = USER)
    UserResponseDto createUser(CreateUserRequestDto dto);
    //TODO: Restrict authorization to own user and ROLE = ADMIN
    UserResponseDto getUserById(UUID id);
    void updateUserById(UUID id, UpdateUserRequestDto dto);
    //TODO: control for transition from CLOSED to ACTIVE or SUSPENDED
    void changeUserStatus(UUID id, UpdateUserStatusDto dto);  // -> requires role = ADMIN
    //void changeUserPassword(UUID id, String password);   // -> private: owned by user
}
