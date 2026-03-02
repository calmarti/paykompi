package com.calmarti.paykompi.domain.account.dto;

import com.calmarti.paykompi.domain.account.enums.AccountCurrency;
import jakarta.validation.constraints.NotNull;

public record CreateAccountRequestDto(
        @NotNull
        AccountCurrency currency){
}
