package com.calmarti.paykompi.user.service;

import com.calmarti.paykompi.user.dto.CreateUserRequestDto;
import com.calmarti.paykompi.user.dto.UpdateUserRequestDto;
import com.calmarti.paykompi.user.dto.UserResponseDto;
import com.calmarti.paykompi.user.enums.UserStatus;
import java.util.UUID;


public interface UserService {
    //UserResponseDto createUser(CreateUserRequestDto request);// -> Will be replaced by /auth/register
    UserResponseDto getUserById(UUID id);
//    UserResponseDto updateUserById(UUID id, UpdateUserRequestDto request);
//    UserResponseDto changeUserStatus(UUID id, UserStatus status);
//    UserResponseDto changeUserPassword(UUID id, String password);
//    UserResponseDto closeUserAcount(UUID id);
}
