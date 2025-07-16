package istad.co.mobilebankingapi.init;

import istad.co.mobilebankingapi.domain.Segment;
import istad.co.mobilebankingapi.repository.SegmentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SegmentData {
    private final SegmentRepository segmentRepository;
    @PostConstruct
    public void init() {
        List<Segment> segments = new ArrayList<>();
        Segment segment1 = new Segment();
        segment1.setSegment("Gold");
        Segment segment2 = new Segment();
        segment2.setSegment("Silver");
        Segment segment3 = new Segment();
        segment3.setSegment("Regular");
        segments.add(segment1);
        segments.add(segment2);
        segments.add(segment3);
        segmentRepository.saveAll(segments);
    }

}
