package bootcamp.ai.response;

public record TextDocumentRequest(
        String filename,
        String content
) {
}
