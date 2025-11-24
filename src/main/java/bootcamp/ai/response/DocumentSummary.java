package bootcamp.ai.response;

import java.time.LocalDateTime;

public record DocumentSummary(
        String id,
        String filename,
        String contentType,
        int chunkCount,
        LocalDateTime createdAt,
        int contentLength
) {
}