package istad.co.mobilebankingapi.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kycs")
// Know Your Customer
public class KYC {
    @Id
    private Integer id;

    private String nationalCardId;

    private Boolean isVerified = false;

    private Boolean isDeleted = false;

    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
