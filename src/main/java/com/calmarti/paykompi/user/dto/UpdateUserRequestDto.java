package com.calmarti.paykompi.user.dto;

import com.calmarti.paykompi.user.enums.UserStatus;
import com.calmarti.paykompi.user.enums.UserType;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record UpdateUserRequestDto(
        String username,
        String email,
        String firstName,
        String lastName
) {
}
