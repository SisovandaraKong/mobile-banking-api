package istad.co.mobilebankingapi.init;

import istad.co.mobilebankingapi.domain.Segment;
import istad.co.mobilebankingapi.enums.SegmentName;
import istad.co.mobilebankingapi.repository.SegmentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
@RequiredArgsConstructor
public class SegmentInit {

    private final SegmentRepository segmentRepository;

    @PostConstruct
    public void init() {
if (segmentRepository.count() == 0) {
    Segment regular = new Segment();
    regular.setSegment(SegmentName.REGULAR);
    Segment silver = new Segment();
    silver.setSegment(SegmentName.SILVER);
    Segment gold = new Segment();
    gold.setSegment(SegmentName.GOLD);
    segmentRepository.saveAll(List.of(regular, silver, gold));
}
    }

}
