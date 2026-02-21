package com.calmarti.paykompi.user.dto;

import com.calmarti.paykompi.user.enums.UserRole;
import com.calmarti.paykompi.user.enums.UserStatus;
import com.calmarti.paykompi.user.enums.UserType;

import java.time.Instant;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String username,
        String email,
        String firstName,
        String lastName,
        UserType userType,
        UserStatus userStatus,
        Instant createdAt
        ){}
