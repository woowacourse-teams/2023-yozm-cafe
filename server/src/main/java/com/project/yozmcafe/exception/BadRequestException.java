package com.project.yozmcafe.exception;

public class BadRequestException extends BaseException {

    public BadRequestException(final ErrorCode code) {
        super(code);
    }
}
