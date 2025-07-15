package istad.co.mobilebankingapi.dto.customer;

import istad.co.mobilebankingapi.domain.KYC;
import istad.co.mobilebankingapi.dto.kyc.KycResponse;

import java.time.LocalDateTime;

public record CustomerResponse(
        String fullName,
        String uuid,
        String gender,
        String email,
        String phoneNumber,
        String segment,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
