package bootcamp.ai.response;

public record SearchResult(
        String id,
        String content,
        java.util.Map<String, Object> metadata
) {
}