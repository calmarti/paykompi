package com.calmarti.paykompi.user.dto;

import com.calmarti.paykompi.user.enums.UserStatus;
import com.calmarti.paykompi.user.enums.UserType;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record UpdateUserRequestDto(
        @NotBlank
        String username,
        @NotBlank
        String email,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName
) {
}
