package com.calmarti.paykompi.user.dto;

public record UpdateUserRequestDto(
        String username,
        String email,
        String firstName,
        String lastName
) {
}
