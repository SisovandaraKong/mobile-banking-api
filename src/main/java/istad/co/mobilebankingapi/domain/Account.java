package istad.co.mobilebankingapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String actNo;

    @Column(nullable = false,length = 40)
    private String actType;

    @Column(nullable = false)
    private String actCurrency;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;
}
