package com.project.yozmcafe.exception;

public class ErrorResponse {

    private final String code;
    private final String message;

    private ErrorResponse(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    static ErrorResponse from(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
