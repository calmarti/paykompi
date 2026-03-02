package com.calmarti.paykompi.domain.user.dto;

public record UpdateUserRequestDto(
        String username,
        String email,
        String firstName,
        String lastName
) {
}
