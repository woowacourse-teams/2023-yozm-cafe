package com.project.yozmcafe.exception;

import com.project.yozmcafe.domain.ImageResizer;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.Detail;

import static java.lang.String.format;

public enum ErrorCode {
    TOKEN_NOT_EXIST("T1", "토큰이 존재하지 않습니다."),
    TOKEN_IS_EXPIRED("T2", "만료된 토큰입니다."),
    INVALID_TOKEN("T3", "잘못된 토큰입니다."),

    NOT_EXISTED_CAFE("C1", "카페가 존재하지 않습니다."),
    DUPLICATED_CAFE_AVAILABLE_TIMES("C2", "중복된 영업 날짜는 불가능합니다."),
    INVALID_CAFE_AVAILABLE_TIMES("C3", "영업 날짜는 일주일 전체 정보가 필요합니다."),
    INVALID_CAFE_NAME("C4", format("카페의 이름은 1 ~ %d자여야 합니다.", Cafe.NAME_MAX_LENGTH)),
    INVALID_CAFE_ADDRESS("C5", format("카페의 주소는 1 ~ %d자여야 합니다.", Cafe.ADDRESS_MAX_LENGTH)),
    INVALID_CAFE_IMAGE_URL("C6", format("카페의 사진 경로 길이는 1 ~ %d 여야 합니다.", Detail.PHONE_MAX_LENGTH)),
    INVALID_CAFE_MAP_URL("C7", format("카페의 지도 경로 길이는 1 ~ %d 여야 합니다.", Detail.MAP_URL_MAX_LENGTH)),
    INVALID_CAFE_PHONE("C8", format("카페의 전화변호 길이는 1 ~ %d 이여야 합니다.", Detail.PHONE_MAX_LENGTH)),
    NOT_EXISTED_CAFE_IMAGE("C9", "카페의 이미지 정보가 최소 1개 이상 필요합니다."),
    NOT_EXISTED_CAFE_DETAIL("C10", "카페의 상세 정보가 필요합니다."),
    INVALID_CAFE_LIKE_COUNT("C11", "카페의 좋아요 수는 음수가 될 수 없습니다."),
    INVALID_CAFE_AVAILABLE_TIME("C12", "영업 시작 시각이 영업 종료 시각보다 이후일 수 없습니다."),

    NOT_EXISTED_MEMBER("M1", "회원이 존재하지 않습니다."),

    NOT_EXISTED_LIKED_CAFE("L1", "Liked Cafe가 존재하지 않습니다."),

    INVALID_OAUTH_USER_INFO("O1", "Provider로 부터 받은 사용자 정보의 확인이 필요합니다."),
    NOT_EXISTED_OAUTH_PROVIDER("O2", "잘못된 Provider Name 입니다."),
    NOT_EXISTED_OAUTH_CLIENT("O3", "일치하는 OAuthClient가 존재하지 않습니다."),

    NOT_IMAGE("I1", "이미지 형식만 받을 수 있습니다."),
    INVALID_IMAGE_SIZE("I2", format("이미지는 최대 %d Byte까지만 가능합니다.", ImageResizer.MAX_IMAGE_SIZE));

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
