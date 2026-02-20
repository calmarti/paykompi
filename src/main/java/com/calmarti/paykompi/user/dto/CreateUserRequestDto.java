package com.calmarti.paykompi.user.dto;

import com.calmarti.paykompi.user.enums.UserStatus;
import com.calmarti.paykompi.user.enums.UserType;

import java.time.Instant;

public record CreateUserRequestDto(
        String email,
        String password,
        String firstName,
        String lastName,
        UserType usertype,
        UserStatus userStatus,
        Instant createdAt
) {
}
