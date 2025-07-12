package istad.co.mobilebankingapi.dto.accountType;

import jakarta.validation.constraints.NotBlank;

public record AccountTypeRequest(
        @NotBlank(message = "Name is required")
        String name
) {
}
