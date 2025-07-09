package istad.co.mobilebankingapi.repository;

import istad.co.mobilebankingapi.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KYCRepository extends JpaRepository<KYC, Integer> {
}
