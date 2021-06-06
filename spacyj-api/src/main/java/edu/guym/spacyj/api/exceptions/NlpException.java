package edu.guym.spacyj.api.exceptions;

public class NlpException extends RuntimeException {

    private final String failedText;

    public NlpException(String failedText) {
        super(String.format("Failed to parse text [%s]", failedText));
        this.failedText = failedText;
    }

    public NlpException(Throwable cause, String failedText) {
        super(String.format("Failed to parse text [%s]. Reason: [%s]", failedText, cause.getMessage()));
        this.failedText = failedText;
    }

    public String getFailedText() {
        return failedText;
    }
}
