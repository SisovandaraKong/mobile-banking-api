package istad.co.mobilebankingapi.repository;

import istad.co.mobilebankingapi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
