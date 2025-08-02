package istad.co.mobilebankingapi.dto.media;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record MediaResponse(
        String name,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String mimeType,
        String uri,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long size
) {
}
