package istad.co.mobilebankingapi.controller;

import istad.co.mobilebankingapi.domain.Account;
import istad.co.mobilebankingapi.dto.account.AccountRequest;
import istad.co.mobilebankingapi.dto.account.AccountUpdate;
import istad.co.mobilebankingapi.dto.account.Withdraw;
import istad.co.mobilebankingapi.repository.AccountRepository;
import istad.co.mobilebankingapi.service.AccountService;
import istad.co.mobilebankingapi.service.AccountTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;
    private final AccountTypeService accountTypeService;
    private final AccountRepository accountRepository;

    @GetMapping
    public ResponseEntity<?> getAllAccounts() {
        return new ResponseEntity<>(Map.of(
                "accounts", accountService.getAllAccounts()
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addNewAccount(@Valid @RequestBody AccountRequest accountRequest) {
        return new ResponseEntity<>(accountService.createAccount(accountRequest), HttpStatus.CREATED);
    }

    @GetMapping("/act-no/{actNo}")
    public ResponseEntity<?> getAccountByActNo(@PathVariable String actNo) {
        return new ResponseEntity<>(accountService.getAccountByActNo(actNo), HttpStatus.OK);
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<?> getAccountByCustomer(@PathVariable String phoneNumber) {
        return new ResponseEntity<>(accountService.getAccountByCustomerPhoneNumber(phoneNumber), HttpStatus.OK);
    }

    @DeleteMapping("/{actNo}")
    public ResponseEntity<?> deleteAccountByActNo(@PathVariable String actNo) {
        accountService.deleteAccountByActNo(actNo);
        return new ResponseEntity<>(Map.of(
                "message", "Account has deleted successfully"
        ),HttpStatus.OK);
    }

    @PatchMapping("/{actNo}")
    public ResponseEntity<?> updateAccountByActNo(@PathVariable String actNo, @Valid @RequestBody AccountUpdate accountUpdate) {
        return new ResponseEntity<>(Map.of(
                "account",accountService.updateAccountByActNo(actNo,accountUpdate)
        ),HttpStatus.OK);
    }

    @PatchMapping("/close/{actNo}")
    public ResponseEntity<?> updateAccountToIsDeletedByActNo(@PathVariable String actNo) {
        accountService.disableAccountByActNo(actNo);
         return new ResponseEntity<>(
                 Map.of(
                         "message", "Account has been disabled successfully"
                 ),HttpStatus.OK);
    }

    @PutMapping("/withdraw/{act}")
    public ResponseEntity<?> withdrawAccountByActNo(@PathVariable String act, @Valid @RequestBody Withdraw withdraw) {
        accountService.withdrawAccountByActNo(act, withdraw);
        return new ResponseEntity<>(Map.of(
                "Account "+act, "Account has been withdrawn successfully"
        ), HttpStatus.CREATED);
    }


}
