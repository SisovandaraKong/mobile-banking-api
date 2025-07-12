package istad.co.mobilebankingapi.dto.customer;

import java.time.LocalDateTime;

public record CustomerResponse(
        String fullName,
        String uuid,
        String gender,
        String email,
        String phoneNumber,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
