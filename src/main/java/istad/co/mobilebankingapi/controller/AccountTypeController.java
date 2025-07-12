package istad.co.mobilebankingapi.controller;


import istad.co.mobilebankingapi.domain.AccountType;
import istad.co.mobilebankingapi.dto.accountType.AccountTypeRequest;
import istad.co.mobilebankingapi.dto.accountType.AccountTypeUpdate;
import istad.co.mobilebankingapi.service.AccountTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account-types")
public class AccountTypeController {
    private final AccountTypeService accountTypeService;

    @GetMapping
    public ResponseEntity<?> getAllAccountTypes() {
        return new ResponseEntity<>(Map.of(
                "accountTypes", accountTypeService.getAllAccountTypes()
        ), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getAccountTypeByUuid(@PathVariable String uuid) {
        return new ResponseEntity<>(
                accountTypeService.getAccountTypeByUuid(uuid), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createAccountType(@Valid @RequestBody AccountTypeRequest accountTypeRequest) {
        return new ResponseEntity<>(
                accountTypeService.createAccountType(accountTypeRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateAccountTypeByUuid(@PathVariable String uuid, @Valid @RequestBody AccountTypeUpdate accountTypeUpdate) {
        return new ResponseEntity<>(
                accountTypeService.updateAccountTypeByUuid(uuid, accountTypeUpdate), HttpStatus.OK
        );
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteAccountTypeByUuid(@PathVariable String uuid) {
        accountTypeService.deleteAccountTypeByUuid(uuid);
        return new ResponseEntity<>(
                Map.of("message", "Account type has deleted"), HttpStatus.OK
        );
    }
}
