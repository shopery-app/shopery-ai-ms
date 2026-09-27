package az.shopery.ai_ms.service.impl;

import az.shopery.ai_ms.dto.request.ChatRequestDto;
import az.shopery.ai_ms.dto.response.ChatResponseDto;
import az.shopery.ai_ms.dto.shared.SuccessResponse;
import az.shopery.ai_ms.handler.exception.ExternalServiceException;
import az.shopery.ai_ms.service.ClaudeService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaudeServiceImpl implements ClaudeService {

    private final ChatClient chatClient;

    @Override
    public SuccessResponse<ChatResponseDto> chat(ChatRequestDto request) {
        return SuccessResponse.of(callClaudeApi(request), "Message processed successfully!");
    }

    private ChatResponseDto callClaudeApi(ChatRequestDto request) {
        try {
            ChatResponse response = chatClient.prompt()
                    .user(request.getMessage())
                    .options(AnthropicChatOptions.builder()
                            .maxTokens(request.getRemainingTokens()))
                    .call()
                    .chatResponse();

            if (Objects.isNull(response) || Objects.isNull(response.getResult())) {
                throw new ExternalServiceException("Empty response from Claude API!");
            }

            String messageContent = response.getResult().getOutput().getText();
            Integer totalTokens = response.getMetadata().getUsage().getTotalTokens();

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
