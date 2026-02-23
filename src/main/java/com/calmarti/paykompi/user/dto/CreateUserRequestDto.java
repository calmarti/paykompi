package com.calmarti.paykompi.user.dto;

import com.calmarti.paykompi.user.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequestDto(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotNull
        UserType userType
) {
}
