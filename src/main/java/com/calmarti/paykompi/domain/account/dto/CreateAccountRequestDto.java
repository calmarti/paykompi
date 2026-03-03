package com.calmarti.paykompi.domain.account.dto;

import com.calmarti.paykompi.common.enums.Currency;
import jakarta.validation.constraints.NotNull;

public record CreateAccountRequestDto(
        @NotNull
        Currency currency){
}
