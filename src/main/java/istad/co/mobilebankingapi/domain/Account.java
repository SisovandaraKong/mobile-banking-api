package istad.co.mobilebankingapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String actNo;

    @Column(nullable = false)
    private String actCurrency;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    private BigDecimal overLimit;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;

    @OneToMany(mappedBy = "sender")
    private List<Transaction> transactionSenders;

    @OneToMany(mappedBy = "receiver")
    private List<Transaction> transactionReceivers;
}
