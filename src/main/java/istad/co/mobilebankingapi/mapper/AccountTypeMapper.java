package istad.co.mobilebankingapi.mapper;

import istad.co.mobilebankingapi.domain.AccountType;
import istad.co.mobilebankingapi.dto.accountType.AccountTypeResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {
    AccountTypeResponse toAccountTypeResponse(AccountType accountType);
}
