package istad.co.mobilebankingapi.repository;

import istad.co.mobilebankingapi.domain.Segment;
import istad.co.mobilebankingapi.enums.SegmentName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface SegmentRepository extends JpaRepository<Segment,Integer> {
    Optional<Segment> findBySegment(SegmentName segment);
}
