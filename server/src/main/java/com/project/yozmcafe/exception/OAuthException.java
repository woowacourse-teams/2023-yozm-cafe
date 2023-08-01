package com.project.yozmcafe.exception;

public class OAuthException extends BaseException {

    public OAuthException(final ErrorCode code) {
        super(code);
    }
}
