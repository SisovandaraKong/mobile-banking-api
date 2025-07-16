package istad.co.mobilebankingapi.dto.account;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record Withdraw(
        @Max(9999)
    Integer pin,
    @Positive
    BigDecimal balance
) {
}
