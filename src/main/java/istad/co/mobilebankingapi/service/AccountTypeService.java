package istad.co.mobilebankingapi.service;

import istad.co.mobilebankingapi.dto.account.AccountRequest;
import istad.co.mobilebankingapi.dto.account.AccountResponse;
import istad.co.mobilebankingapi.dto.accountType.AccountTypeRequest;
import istad.co.mobilebankingapi.dto.accountType.AccountTypeResponse;
import istad.co.mobilebankingapi.dto.accountType.AccountTypeUpdate;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeResponse> getAllAccountTypes();
    AccountTypeResponse getAccountTypeByUuid(String uuid);
    AccountTypeResponse createAccountType(AccountTypeRequest accountTypeRequest);
    AccountTypeResponse updateAccountTypeByUuid(String uuid, AccountTypeUpdate accountTypeUpdate);
    void deleteAccountTypeByUuid(String uuid);
}
