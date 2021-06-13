package edu.guym.spacyj.api.exceptions;

public class SpacyException extends RuntimeException {

    private final String failedText;

    public SpacyException(String failedText) {
        super(String.format("Failed to parse text [%s]", failedText));
        this.failedText = failedText;
    }

    public SpacyException(Throwable cause, String failedText) {
        super(String.format("Failed to parse text [%s]. Reason: [%s]", failedText, cause.getMessage()));
        this.failedText = failedText;
    }

    public String getFailedText() {
        return failedText;
    }
}
