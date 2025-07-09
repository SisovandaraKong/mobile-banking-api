package istad.co.mobilebankingapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kycs")
// Know Your Customer
public class KYC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nationalCardId;

    private Boolean isVerified;

    private Boolean isDeleted;

    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
