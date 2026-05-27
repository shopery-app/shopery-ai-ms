package az.shopery.ai_ms.service;

import az.shopery.ai_ms.dto.request.ChatRequestDto;
import az.shopery.ai_ms.dto.response.ChatResponseDto;
import az.shopery.ai_ms.dto.shared.SuccessResponse;

public interface ClaudeService {
    SuccessResponse<ChatResponseDto> chat(ChatRequestDto chatRequestDto);
}
