package com.project.yozmcafe.exception;

public enum ErrorCode {
    TOKEN_NOT_EXIST("T1", "토큰이 존재하지 않습니다."),
    TOKEN_IS_EXPIRED("T2", "만료된 토큰입니다."),
    INVALID_TOKEN("T3", "잘못된 토큰입니다."),
    NOT_EXISTED_CAFE("C1", "카페가 존재하지 않습니다."),
    NOT_EXISTED_MEMBER("M1", "회원이 존재하지 않습니다."),
    NOT_EXISTED_UN_VIEWED_CAFE("U1", "UnViewed Cafe가 존재하지 않습니다."),
    NOT_EXISTED_LIKED_CAFE("L1", "Liked Cafe가 존재하지 않습니다."),
    NOT_EXISTED_CAFE_IMAGE("I1", "카페의 이미지가 존재하지 않습니다."),
    INVALID_OAUTH_USER_INFO("OA1", "Provider로 부터 받은 사용자 정보의 확인이 필요합니다."),
    NOT_EXISTED_OAUTH_PROVIDER("OP1", "잘못된 Provider Name 입니다."),
    NOT_EXISTED_OAUTH_CLIENT("OC1", "일치하는 OAuthClient가 존재하지 않습니다.");

    private final String code;
    private final String message;

    ErrorCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
