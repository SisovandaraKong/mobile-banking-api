package istad.co.mobilebankingapi.service.impl;

import istad.co.mobilebankingapi.domain.Account;
import istad.co.mobilebankingapi.domain.AccountType;
import istad.co.mobilebankingapi.domain.Customer;
import istad.co.mobilebankingapi.domain.Segment;
import istad.co.mobilebankingapi.dto.account.AccountRequest;
import istad.co.mobilebankingapi.dto.account.AccountResponse;
import istad.co.mobilebankingapi.dto.account.AccountUpdate;
import istad.co.mobilebankingapi.dto.account.Withdraw;
import istad.co.mobilebankingapi.dto.customer.CustomerResponse;
import istad.co.mobilebankingapi.dto.customer.UpdateCustomer;
import istad.co.mobilebankingapi.enums.AccountTypeName;
import istad.co.mobilebankingapi.enums.CurrencyName;
import istad.co.mobilebankingapi.enums.SegmentName;
import istad.co.mobilebankingapi.mapper.AccountMapper;
import istad.co.mobilebankingapi.repository.AccountRepository;
import istad.co.mobilebankingapi.repository.AccountTypeRepository;
import istad.co.mobilebankingapi.repository.CustomerRepository;
import istad.co.mobilebankingapi.repository.SegmentRepository;
import istad.co.mobilebankingapi.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerRepository customerRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final SegmentRepository segmentRepository;

    @Override
    public List<AccountResponse> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .filter(account -> account.getIsDeleted().equals(false))
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) {


        Customer customer = customerRepository.findByPhoneNumber(accountRequest.customerPhoneNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        if (customerRepository.existsCustomerByKyc_IsVerified(false)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer need to verified");
        }
        AccountTypeName accountTypeName = AccountTypeName.valueOf(accountRequest.accountType().toUpperCase());
        if (!accountTypeRepository.existsByName(accountTypeName)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account type not found");
        }

        AccountType accountType = accountTypeRepository.findByName(accountTypeName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account type name not found"));

//        if (accountRepository.existsByAccountType(accountType)) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account type already exists");
//        }


        Account account = new Account();
        String actNo;
        do {
            actNo = String.format("%09d", new Random().nextInt(1_000_000_000)); // Max: 999,999,999
        } while (accountRepository.existsByActNo(actNo));
        account.setActNo(actNo);
        CurrencyName currencyName = CurrencyName.valueOf(accountRequest.actCurrency().toUpperCase());
        account.setActCurrency(currencyName);
        SegmentName segment = customer.getSegment().getSegment();
        switch (segment){
            case SegmentName.REGULAR -> account.setOverLimit(BigDecimal.valueOf(5000));
            case SegmentName.SILVER -> account.setOverLimit(BigDecimal.valueOf(10000));
            case SegmentName.GOLD-> account.setOverLimit(BigDecimal.valueOf(50000));
        }
        account.setCustomer(customer);
        account.setAccountType(accountType);
        account = accountRepository.save(account);

        return accountMapper.toAccountResponse(account);
    }

    @Override
    public AccountResponse getAccountByActNo(String actNo) {

        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account's number not found"));
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public AccountResponse getAccountByCustomerPhoneNumber(String customerPhoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(customerPhoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        Account account = accountRepository.findByCustomer(customer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        return accountMapper.toAccountResponse(account);
    }

    @Override
    @Transactional
    public void deleteAccountByActNo(String actNo) {
    if (!accountRepository.existsByActNo(actNo)) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account's number not found");
    }
    accountRepository.deleteByActNo(actNo);
    }

    @Override
    public AccountResponse updateAccountByActNo(String actNo, AccountUpdate accountUpdate) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account's number not found"));

        account.setBalance(accountUpdate.balance());
        account = accountRepository.save(account);
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public void disableAccountByActNo(String actNo) {
    Account account = accountRepository.findByActNo(actNo)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    if (account.getIsDeleted().equals(true)) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Account is already deleted");
    }
    account.setIsDeleted(true);
    accountRepository.save(account);
    }

    @Override
    public void withdrawAccountByActNo(String actNo, Withdraw withdraw) {
        if (!customerRepository.existsByPin(withdraw.pin())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account pin is not valid");
        }
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account's number not found"));
        account.setBalance(account.getBalance().subtract(withdraw.balance()));
        accountRepository.save(account);
    }
}
