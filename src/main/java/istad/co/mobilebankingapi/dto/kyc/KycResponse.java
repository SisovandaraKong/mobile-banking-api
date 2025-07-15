package istad.co.mobilebankingapi.dto.kyc;

import istad.co.mobilebankingapi.domain.Customer;
import istad.co.mobilebankingapi.dto.customer.CustomerResponse;

public record KycResponse(
        String nationalCardId,
        CustomerResponse customer,
        Boolean isVerified
) {
}
