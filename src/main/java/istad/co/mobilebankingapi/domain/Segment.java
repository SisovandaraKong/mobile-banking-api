package istad.co.mobilebankingapi.domain;

import istad.co.mobilebankingapi.enums.SegmentName;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "segments")
public class Segment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private SegmentName segment;

    @OneToMany(mappedBy = "segment")
    private List<Customer> customers;

    private Boolean isDeleted = false;
}
