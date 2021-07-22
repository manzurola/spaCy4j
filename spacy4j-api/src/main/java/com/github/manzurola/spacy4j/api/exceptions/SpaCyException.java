package com.github.manzurola.spacy4j.api.exceptions;

public class SpaCyException extends RuntimeException {

    private final String failedText;

    public SpaCyException(String failedText) {
        super(String.format("Failed to parse text [%s]", failedText));
        this.failedText = failedText;
    }

    public SpaCyException(Throwable cause, String failedText) {
        super(String.format("Failed to parse text [%s]. Reason: [%s]", failedText, cause.getMessage()));
        this.failedText = failedText;
    }

    public String getFailedText() {
        return failedText;
    }
}
