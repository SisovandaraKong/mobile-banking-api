package istad.co.mobilebankingapi.repository;

import istad.co.mobilebankingapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    Customer findByEmail(String email);
}
