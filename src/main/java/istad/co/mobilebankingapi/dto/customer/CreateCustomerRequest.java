package istad.co.mobilebankingapi.dto.customer;

import istad.co.mobilebankingapi.enums.SegmentName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequest(
        @Max(9999)
        Integer pin,
        @NotBlank(message = "Full name is required")
            @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
        String fullName,

        @NotBlank(message = "Email is required")
            @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Gender is required")
        String gender,

        String phoneNumber,

        @NotBlank(message = "Remark is required")
        String remark,

        String nationalCardId,

        String segment
) {
}
