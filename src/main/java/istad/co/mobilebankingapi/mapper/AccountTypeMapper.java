package istad.co.mobilebankingapi.mapper;

import istad.co.mobilebankingapi.domain.AccountType;
import istad.co.mobilebankingapi.dto.accountType.AccountTypeRequest;
import istad.co.mobilebankingapi.dto.accountType.AccountTypeResponse;
import istad.co.mobilebankingapi.dto.accountType.AccountTypeUpdate;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {
    AccountTypeResponse toAccountTypeResponse(AccountType accountType);
    AccountType requestToAccountType(AccountTypeRequest accountTypeRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateToAccountType(AccountTypeUpdate accountTypeUpdate,@MappingTarget AccountType accountType);
}
