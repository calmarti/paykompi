package com.calmarti.paykompi.domain.account.dto;

import com.calmarti.paykompi.domain.account.enums.AccountStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateAccountStatusDto(
        @NotNull
        AccountStatus accountStatus
) {
}
