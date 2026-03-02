package com.calmarti.paykompi.domain.user.dto;

import com.calmarti.paykompi.domain.user.enums.UserStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateUserStatusDto(
        @NotNull
        UserStatus userStatus
) {}
