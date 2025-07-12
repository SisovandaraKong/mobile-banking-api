package istad.co.mobilebankingapi.dto.account;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountUpdate(
        @NotNull(message = "Balance is required")
        BigDecimal balance,

        Boolean isDeleted
) {
}
