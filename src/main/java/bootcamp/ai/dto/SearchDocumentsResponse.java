package bootcamp.ai.dto;

import java.util.List;

/**
 * 문서 검색 응답
 */
public record SearchDocumentsResponse(
        String query,
        int resultCount,
        List<DocumentInfo> documents
) {}
