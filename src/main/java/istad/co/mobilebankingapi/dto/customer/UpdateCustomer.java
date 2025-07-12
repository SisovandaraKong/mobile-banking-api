package istad.co.mobilebankingapi.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCustomer(
        @NotBlank(message = "Full name is required")
            @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
        String fullName,

        @NotBlank(message = "Email is required")
            @Email(message = "Email should be valid")
        String email,

        String phoneNumber,

        @NotBlank(message = "Remark is required")
        String remark
) {
}
