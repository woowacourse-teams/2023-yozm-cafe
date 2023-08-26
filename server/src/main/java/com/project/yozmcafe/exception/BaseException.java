package com.project.yozmcafe.exception;

public class BaseException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public BaseException(final ErrorCode code) {
        this.errorResponse = ErrorResponse.from(code);
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    @Override
    public String getMessage() {
        return errorResponse.getMessage();
    }
}
