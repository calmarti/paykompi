package com.calmarti.paykompi.user.dto;

import com.calmarti.paykompi.user.enums.UserStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateUserStatusDto(
        @NotNull
        UserStatus userStatus
) {}
