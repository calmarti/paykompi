package com.calmarti.paykompi.domain.user.service;

import com.calmarti.paykompi.domain.user.dto.CreateUserRequestDto;
import com.calmarti.paykompi.domain.user.dto.UpdateUserRequestDto;
import com.calmarti.paykompi.domain.user.dto.UpdateUserStatusDto;
import com.calmarti.paykompi.domain.user.dto.UserResponseDto;

import java.util.UUID;


public interface UserService {
     //TODO: Include userRole as optional request field (default = USER) => refactor dtos, mapper, user service (maybe it's already done)
    UserResponseDto createUser(CreateUserRequestDto dto);
    UserResponseDto getUserById(UUID id);
    void updateUserById(UUID id, UpdateUserRequestDto dto);
    //TODO: control for transition from CLOSED to ACTIVE or SUSPENDED
    void changeUserStatus(UUID id, UpdateUserStatusDto dto);  // -> requires role = ADMIN
    //void changeUserPassword(UUID id, String password);   // -> private: owned by user
}
