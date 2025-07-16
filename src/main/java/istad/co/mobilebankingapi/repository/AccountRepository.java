package istad.co.mobilebankingapi.repository;

import istad.co.mobilebankingapi.domain.Account;
import istad.co.mobilebankingapi.domain.AccountType;
import istad.co.mobilebankingapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsByActNo(String actNo);
    Optional<Account> findByActNo(String actNo);
    Optional<Account> findByCustomer(Customer customer);
    void deleteByActNo(String actNo);
    boolean existsByAccountType(AccountType accountType);
    boolean existsByAccountType_Uuid(String uuid);
}
