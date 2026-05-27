package az.shopery.ai_ms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient claudeWebClient(ClaudeApiConfig config) {
        return RestClient.builder()
                .baseUrl(config.getUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("x-api-key", config.getKey())
                .defaultHeader("anthropic-version", config.getVersion())
                .build();
    }
}
