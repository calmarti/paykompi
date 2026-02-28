package com.calmarti.paykompi.account.dto;

import com.calmarti.paykompi.account.enums.AccountCurrency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAccountRequestDto(
        @NotNull
        AccountCurrency currency){
}
