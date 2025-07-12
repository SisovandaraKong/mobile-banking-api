package istad.co.mobilebankingapi.dto.accountType;

import jakarta.validation.constraints.NotBlank;

public record AccountTypeUpdate(
        @NotBlank(message = "Name is required")
        String name
) {
}
