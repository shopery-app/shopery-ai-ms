package az.shopery.ai_ms.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "claude.api")
public class ClaudeApiConfig {
    String key;
    String url;
    String model;
    Integer maxTokens;
    String version;
}
