package bootcamp.ai.dto;

import java.util.Map;

/**
 * 문서 정보
 */
public record DocumentInfo(
        String id,
        String content,
        Map<String, Object> metadata
) {}
