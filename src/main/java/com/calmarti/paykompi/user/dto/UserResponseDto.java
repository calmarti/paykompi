package com.calmarti.paykompi.user.dto;

import com.calmarti.paykompi.user.enums.UserStatus;
import com.calmarti.paykompi.user.enums.UserType;

import java.time.Instant;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        String firstName,
        String lastName,
        UserType usertype,
        UserStatus userStatus,
        Instant createdAt
        ){}
