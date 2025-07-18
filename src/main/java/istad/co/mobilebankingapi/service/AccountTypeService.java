package istad.co.mobilebankingapi.service;

import istad.co.mobilebankingapi.dto.accountType.AccountTypeResponse;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeResponse> getAllAccountTypes();
    AccountTypeResponse getAccountTypeByUuid(String uuid);
}
