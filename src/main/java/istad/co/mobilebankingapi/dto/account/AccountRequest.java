package istad.co.mobilebankingapi.dto.account;

import istad.co.mobilebankingapi.enums.AccountTypeName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AccountRequest(
        @NotBlank(message = "Account currency is required")
        String actCurrency,

        @NotBlank(message = "Customer phone number is required")
        String customerPhoneNumber,

        @NotBlank(message = "Account type Name is required")
        String accountType
) {
}
