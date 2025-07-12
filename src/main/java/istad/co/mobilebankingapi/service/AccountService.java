package istad.co.mobilebankingapi.service;

import istad.co.mobilebankingapi.domain.Account;
import istad.co.mobilebankingapi.dto.account.AccountRequest;
import istad.co.mobilebankingapi.dto.account.AccountResponse;
import istad.co.mobilebankingapi.dto.account.AccountUpdate;

import java.util.List;

public interface AccountService {
    List<AccountResponse> getAllAccounts();
    AccountResponse createAccount(AccountRequest accountRequest);
    AccountResponse getAccountByActNo(String actNo);
    AccountResponse getAccountByCustomerPhoneNumber(String customerPhoneNumber);
    void deleteAccountByActNo(String actNo);
    AccountResponse updateAccountByActNo(String actNo, AccountUpdate accountUpdate);
    void disableAccountByActNo(String actNo);
}
