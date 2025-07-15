package istad.co.mobilebankingapi.repository;

import istad.co.mobilebankingapi.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
    Optional<AccountType> findByUuid(String uuid);
    boolean existsByName(String name);
    boolean existsByUuid(String uuid);
}
