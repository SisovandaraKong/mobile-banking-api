package istad.co.mobilebankingapi.init;

import istad.co.mobilebankingapi.domain.AccountType;
import istad.co.mobilebankingapi.enums.AccountTypeName;
import istad.co.mobilebankingapi.repository.AccountTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountTypeInit {
    private final AccountTypeRepository accountTypeRepository;
    @PostConstruct
    void init() {
        if (accountTypeRepository.count() == 0) {
            AccountType payment = new AccountType();
            payment.setName(AccountTypeName.PAYMENT);
            AccountType saving = new AccountType();
            saving.setName(AccountTypeName.SAVING);
            AccountType card = new AccountType();
            card.setName(AccountTypeName.CARD);
            accountTypeRepository.saveAll(List.of(payment, saving, card));
        }
    }
}
