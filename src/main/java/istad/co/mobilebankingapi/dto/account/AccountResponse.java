package istad.co.mobilebankingapi.dto.account;

import istad.co.mobilebankingapi.domain.Customer;
import istad.co.mobilebankingapi.dto.customer.CustomerResponse;

import java.math.BigDecimal;

public record AccountResponse(
        String actNo,
        String actCurrency,
        BigDecimal balance,
        CustomerResponse customer,
        String accountTypeName
) {
}
