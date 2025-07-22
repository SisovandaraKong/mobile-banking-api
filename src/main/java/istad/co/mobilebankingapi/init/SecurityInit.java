package istad.co.mobilebankingapi.init;

import istad.co.mobilebankingapi.domain.Role;
import istad.co.mobilebankingapi.domain.User;
import istad.co.mobilebankingapi.repository.RoleRepository;
import istad.co.mobilebankingapi.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityInit {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @PostConstruct
    public void init() {
        Role defaultRole = new Role();
        defaultRole.setRole("USER");
        defaultRole.setIsDeleted(false);
        Role customer = new Role();
        customer.setRole("CUSTOMER");
        customer.setIsDeleted(false);
        Role admin = new Role();
        admin.setRole("ADMIN");
        admin.setIsDeleted(false);
        Role staff = new Role();
        staff.setRole("STAFF");
        staff.setIsDeleted(false);
    if (roleRepository.count() == 0) {
        roleRepository.saveAll(List.of(defaultRole,customer,admin,staff));
    }

    if (userRepository.count() == 0) {
        User userAdmin = new User();
        userAdmin.setUsername("admin");
        userAdmin.setPassword(passwordEncoder.encode("qwer1234"));
        userAdmin.setIsDeleted(false);
        userAdmin.setIsBlocked(false);
        userAdmin.setIsEnabled(true);
        userAdmin.setRoles(List.of(defaultRole,admin));
        User userStaff = new User();
        userStaff.setUsername("staff");
        userStaff.setPassword(passwordEncoder.encode("qwer1234"));
        userStaff.setIsDeleted(false);
        userStaff.setIsBlocked(false);
        userStaff.setIsEnabled(true);
        userStaff.setRoles(List.of(defaultRole,staff));
        User userCustomer = new User();
        userCustomer.setUsername("customer");
        userCustomer.setPassword(passwordEncoder.encode("qwer1234"));
        userCustomer.setIsDeleted(false);
        userCustomer.setIsBlocked(false);
        userCustomer.setIsEnabled(true);
        userCustomer.setRoles(List.of(defaultRole,customer, staff));
        userRepository.saveAll(List.of(userAdmin,userStaff,userCustomer));
    }
    }
}
