package istad.co.mobilebankingapi.service.impl;

import istad.co.mobilebankingapi.domain.AccountType;
import istad.co.mobilebankingapi.dto.accountType.AccountTypeRequest;
import istad.co.mobilebankingapi.dto.accountType.AccountTypeResponse;
import istad.co.mobilebankingapi.dto.accountType.AccountTypeUpdate;
import istad.co.mobilebankingapi.mapper.AccountTypeMapper;
import istad.co.mobilebankingapi.repository.AccountTypeRepository;
import istad.co.mobilebankingapi.service.AccountTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService {
    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;
    @Override
    public List<AccountTypeResponse> getAllAccountTypes() {
        List<AccountType> accountTypes = accountTypeRepository.findAll();
        return accountTypes.stream()
                .map(accountTypeMapper::toAccountTypeResponse)
                .toList();
    }

    @Override
    public AccountTypeResponse getAccountTypeByUuid(String uuid) {
        AccountType accountType = accountTypeRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account type's uuid not found"));
        return accountTypeMapper.toAccountTypeResponse(accountType);
    }

    @Override
    public AccountTypeResponse createAccountType(AccountTypeRequest accountTypeRequest) {
        if (accountTypeRepository.existsByName(accountTypeRequest.name())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Account type's name already exists");
        }
        AccountType accountType = accountTypeMapper.requestToAccountType(accountTypeRequest);
        accountType = accountTypeRepository.save(accountType);
        return accountTypeMapper.toAccountTypeResponse(accountType);
    }

    @Override
    public AccountTypeResponse updateAccountTypeByUuid(String uuid, AccountTypeUpdate accountTypeUpdate) {
        AccountType accountType = accountTypeRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account type's uuid not found"));
        accountTypeMapper.updateToAccountType(accountTypeUpdate, accountType);
        accountType = accountTypeRepository.save(accountType);
        return accountTypeMapper.toAccountTypeResponse(accountType);
    }

    @Override
    public void deleteAccountTypeByUuid(String uuid) {
    AccountType accountType = accountTypeRepository.findByUuid(uuid)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account type's uuid not found"));
    accountTypeRepository.delete(accountType);
    }
}
