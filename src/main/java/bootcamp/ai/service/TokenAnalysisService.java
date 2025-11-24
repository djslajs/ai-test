package bootcamp.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TokenAnalysisService {

    /**
     * 토큰 사용량 상세 분석
     */
    public void analyzeTokenUsage(ChatResponse response) {
        var metadata = response.getMetadata();
        if (metadata == null || metadata.getUsage() == null) {
            log.warn("Token usage information not available");
            return;
        }

        Usage usage = metadata.getUsage();

        Integer promptTokens = usage.getPromptTokens();
        Integer generationTokens = usage.getCompletionTokens();
        Integer totalTokens = usage.getTotalTokens();

        // 상세 로그
        log.info("=== Token Usage Analysis ===");
        log.info("Prompt Tokens (Input): {} tokens", promptTokens);
        log.info("Generation Tokens (Output): {} tokens", generationTokens);
        log.info("Total Tokens: {} tokens", totalTokens);

        // 비율 계산
        double outputRatio = (generationTokens * 100.0) / totalTokens;
        log.info("Output Ratio: {:.2f}%", outputRatio);

        // 비용 추정 (Claude Sonnet 4.5 기준)
        double estimatedCost = calculateCost(promptTokens.longValue(), generationTokens.longValue());
        log.info("Estimated Cost: ${:.6f}", estimatedCost);

        // 경고: 높은 토큰 사용
        if (totalTokens > 10000) {
            log.warn("⚠️ High token usage detected: {} tokens", totalTokens);
        }

        // 경고: 컨텍스트 윈도우 근접
        int contextWindow = 200000; // Claude Sonnet 4.5: 200K tokens
        if (totalTokens > contextWindow * 0.8) {
            log.warn("⚠️ Approaching context window limit: {}/{} tokens",
                    totalTokens, contextWindow);
        }
    }

    /**
     * 비용 계산 (Claude Sonnet 4.5)
     */
    private double calculateCost(long promptTokens, long generationTokens) {
        double inputPrice = 3.0;   // per 1M tokens
        double outputPrice = 15.0; // per 1M tokens

        double inputCost = (promptTokens / 1_000_000.0) * inputPrice;
        double outputCost = (generationTokens / 1_000_000.0) * outputPrice;

        return inputCost + outputCost;
    }

    /**
     * 대화 이력 정리 필요 여부 판단
     */
    public boolean shouldTrimHistory(List<Message> history) {
        // 간단한 추정: 메시지당 평균 50 토큰
        int estimatedTokens = history.size() * 50;
        int maxHistoryTokens = 50000; // 임계값

        if (estimatedTokens > maxHistoryTokens) {
            log.info("History too long ({} tokens estimated), trimming recommended",
                    estimatedTokens);
            return true;
        }
        return false;
    }
}