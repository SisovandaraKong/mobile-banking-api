package istad.co.mobilebankingapi.mapper;

import istad.co.mobilebankingapi.domain.Account;
import istad.co.mobilebankingapi.dto.account.AccountRequest;
import istad.co.mobilebankingapi.dto.account.AccountResponse;
import istad.co.mobilebankingapi.dto.customer.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {
        AccountTypeMapper.class, CustomerMapper.class
})
public interface AccountMapper {
    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "accountType.name", target = "accountTypeName")
    AccountResponse toAccountResponse(Account account);

    Account fromAccountRequest(AccountRequest accountRequest);
}
