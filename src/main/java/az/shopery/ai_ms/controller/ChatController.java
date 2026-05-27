package az.shopery.ai_ms.controller;

import az.shopery.ai_ms.dto.request.ChatRequestDto;
import az.shopery.ai_ms.dto.response.ChatResponseDto;
import az.shopery.ai_ms.dto.shared.SuccessResponse;
import az.shopery.ai_ms.service.ClaudeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai/chat")
public class ChatController {

    private final ClaudeService claudeService;

    @PostMapping
    public ResponseEntity<SuccessResponse<ChatResponseDto>> chat(@Valid @RequestBody ChatRequestDto chatRequestDto) {
        return ResponseEntity.ok(claudeService.chat(chatRequestDto));
    }
}
