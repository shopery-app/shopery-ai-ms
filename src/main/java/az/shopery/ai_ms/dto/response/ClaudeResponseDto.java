package az.shopery.ai_ms.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClaudeResponseDto {
    String id;
    String type;
    String role;
    String model;

    List<Content> content;

    @JsonProperty("stop_reason")
    String stopReason;

    Usage usage;

    @Data
    public static class Content {
        String type;
        String text;
    }

    @Data
    public static class Usage {
        @JsonProperty("input_tokens")
        Integer inputTokens;
        @JsonProperty("output_tokens")
        Integer outputTokens;
    }
}
