package com.calmarti.paykompi.user.dto;

import com.calmarti.paykompi.user.enums.UserStatus;
import com.calmarti.paykompi.user.enums.UserType;

import java.time.Instant;

public record UpdateUserRequestDto(
        String email,
        String firstName,
        String lastName
) {
}
