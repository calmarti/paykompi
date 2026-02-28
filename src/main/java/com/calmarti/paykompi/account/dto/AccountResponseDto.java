package com.calmarti.paykompi.account.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AccountResponseDto(
        UUID id
) {}
