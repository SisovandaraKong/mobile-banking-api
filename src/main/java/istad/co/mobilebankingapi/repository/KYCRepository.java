package istad.co.mobilebankingapi.repository;

import istad.co.mobilebankingapi.domain.Customer;
import istad.co.mobilebankingapi.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KYCRepository extends JpaRepository<KYC, Integer> {
    Optional<KYC> findByNationalCardId(String nationalCardId);
    Optional<KYC> findByCustomer(Customer customer);
}
