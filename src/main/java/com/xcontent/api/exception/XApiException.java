package com.xcontent.api.exception;

public class XApiException extends RuntimeException {
    private final int statusCode;
    private final String errorCode;

    public XApiException(String message, int statusCode, String errorCode) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    public XApiException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = 0;
        this.errorCode = null;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public boolean isRateLimitError() {
        return statusCode == 429;
    }

    public boolean isAuthenticationError() {
        return statusCode == 401;
    }
}
