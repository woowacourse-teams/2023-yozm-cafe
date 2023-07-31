package com.project.yozmcafe.exception;

public class InternalServerException extends BaseException {

    public InternalServerException(final ErrorCode code) {
        super(code);
    }
}
