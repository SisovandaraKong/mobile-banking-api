package istad.co.mobilebankingapi.repository;

import istad.co.mobilebankingapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Customer> findByEmail(String email);
    void deleteByUuid(String uuid);
    boolean existsByUuid(String uuid);
    Optional<Customer> findByUuid(String uuid);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    void deleteByPhoneNumber(String phoneNumber);
    Optional<Customer> findCustomerByKyc_NationalCardId(String nationalCardId);
}
