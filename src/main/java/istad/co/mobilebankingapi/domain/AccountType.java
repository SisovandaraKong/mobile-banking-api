package istad.co.mobilebankingapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
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
    private String name;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "accountType")
    private List<Account> accounts;
}
