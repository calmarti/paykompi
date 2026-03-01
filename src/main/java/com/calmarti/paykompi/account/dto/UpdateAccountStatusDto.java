package com.calmarti.paykompi.account.dto;

import com.calmarti.paykompi.account.enums.AccountStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateAccountStatusDto(
        @NotNull
        AccountStatus accountStatus
) {
}
