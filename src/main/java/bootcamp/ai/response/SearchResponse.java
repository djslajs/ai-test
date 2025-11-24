package bootcamp.ai.response;

import java.util.List;

public record SearchResponse(
        String documentId,
        String query,
        int resultCount,
        List<SearchResult> results
) {
}
