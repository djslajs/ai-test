package bootcamp.ai.dto;

public record ChatRequestV2(
        String message,
        String conversationId
) {
    public ChatRequestV2 {
        if(message == null || message.isBlank()) {
            throw new IllegalArgumentException("message is null or blank");
        }
    }
}

