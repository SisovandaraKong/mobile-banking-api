package istad.co.mobilebankingapi.domain;

import istad.co.mobilebankingapi.enums.AccountTypeName;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account_types")
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String uuid= UUID.randomUUID().toString();

    @Column(unique = true, nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private AccountTypeName name;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "accountType")
    private List<Account> accounts;
}
