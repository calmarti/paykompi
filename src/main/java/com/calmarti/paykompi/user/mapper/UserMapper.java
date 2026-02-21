package com.calmarti.paykompi.user.mapper;

import com.calmarti.paykompi.user.dto.CreateUserRequestDto;
import com.calmarti.paykompi.user.dto.UserResponseDto;
import com.calmarti.paykompi.user.entity.User;

public class UserMapper {
    public static User toEntity(CreateUserRequestDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setUserType(dto.userType());
        return user;
    }

    public static UserResponseDto toResponse(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserType(),
                user.getUserStatus(),
                user.getCreatedAt());
    }

}
