package bootcamp.ai.controller;

import bootcamp.ai.dto.ChatRequestV2;
import bootcamp.ai.dto.ChatResponseV2;
import bootcamp.ai.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AI Chat", description = "AI 챗봇 API")
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiChatController {

    private final ChatService chatService;

    @Operation(summary = "기본 채팅", description = "AI와 기본 대화를 수행합니다")
    @PostMapping("/chat")
    public ChatResponseV2 chat(@RequestBody ChatRequestV2 request) {
        String response = chatService.chat(request.message());
        return ChatResponseV2.of(response, request.conversationId());
    }

    @Operation(summary = "컨텍스트 기반 채팅", description = "System Message를 활용한 대화")
    @PostMapping("/chat/context")
    public ChatResponseV2 chatWithContext(@RequestBody ChatRequestV2 request) {
        String response = chatService.chatWithContext(request.message());
        return ChatResponseV2.of(response, request.conversationId());
    }
}