package istad.co.mobilebankingapi.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AccountRequest(
        @NotBlank(message = "Account currency is required")
        String actCurrency,

        @NotBlank(message = "Customer phone number is required")
        String customerPhoneNumber,

        @NotBlank(message = "Account type UUID is required")
            @Size(min = 36, max = 36, message = "Account type UUID must be a valid UUID string")
        String accountTypeUuid
) {
}
