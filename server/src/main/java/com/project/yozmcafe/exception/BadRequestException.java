package com.project.yozmcafe.exception;

public class BadRequestException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public BadRequestException(final ErrorCode code) {
        this.errorResponse = ErrorResponse.from(code);
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
