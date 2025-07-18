package istad.co.mobilebankingapi.repository;

import istad.co.mobilebankingapi.domain.AccountType;
import istad.co.mobilebankingapi.enums.AccountTypeName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
    Optional<AccountType> findByUuid(String uuid);
    Optional<AccountType> findByName(AccountTypeName name);
    boolean existsByUuid(String uuid);
    boolean existsByName(AccountTypeName name);
}
