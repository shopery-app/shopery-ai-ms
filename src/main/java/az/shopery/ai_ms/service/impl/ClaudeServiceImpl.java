package az.shopery.ai_ms.service.impl;

import az.shopery.ai_ms.config.ClaudeApiConfig;
import az.shopery.ai_ms.dto.request.ChatRequestDto;
import az.shopery.ai_ms.dto.request.ClaudeRequestDto;
import az.shopery.ai_ms.dto.response.ChatResponseDto;
import az.shopery.ai_ms.dto.response.ClaudeResponseDto;
import az.shopery.ai_ms.dto.shared.SuccessResponse;
import az.shopery.ai_ms.handler.exception.ExternalServiceException;
import az.shopery.ai_ms.service.ClaudeService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaudeServiceImpl implements ClaudeService {

    private final ClaudeApiConfig config;
    private final RestClient restClient;

    @Override
    public SuccessResponse<ChatResponseDto> chat(ChatRequestDto request) {
        ClaudeRequestDto claudeRequest = ClaudeRequestDto.builder()
                .model(config.getModel())
                .maxTokens(request.getRemainingTokens())
                .messages(List.of(
                        ClaudeRequestDto.Message.builder()
                                .role("user")
                                .content(request.getMessage())
                                .build()
                ))
                .build();

        return SuccessResponse.of(callClaudeApi(claudeRequest), "Message processed successfully!");
    }

    private ChatResponseDto callClaudeApi(ClaudeRequestDto request) {
        try {
            ClaudeResponseDto response = restClient.post()
                    .body(request)
                    .retrieve()
                    .body(ClaudeResponseDto.class);

            if (Objects.isNull(response) || Objects.isNull(response.getContent()) || response.getContent().isEmpty()) {
                throw new ExternalServiceException("Empty response from Claude API!");
            }

            String messageContent = response.getContent().getFirst().getText();
            Integer totalTokens = response.getUsage().getInputTokens() + response.getUsage().getOutputTokens();

            log.info("Claude API response received. Tokens used: {}", totalTokens);

            return ChatResponseDto.builder()
                    .message(messageContent)
                    .tokensUsed(totalTokens)
                    .build();

        } catch (Exception e) {
            log.error("Unexpected error during Claude API call: ", e);
            throw new ExternalServiceException("An error occurred while processing your request!");
        }
    }
}
