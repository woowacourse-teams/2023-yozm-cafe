package com.project.yozmcafe.exception;

public class TokenException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public TokenException(final ErrorCode code) {
        this.errorResponse = ErrorResponse.from(code);
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
