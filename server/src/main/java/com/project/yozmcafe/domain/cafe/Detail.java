package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.domain.cafe.available.AvailableTime;
import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;

import java.util.List;

import static com.project.yozmcafe.exception.ErrorCode.DUPLICATED_CAFE_AVAILABLE_TIMES;
import static com.project.yozmcafe.exception.ErrorCode.INVALID_CAFE_AVAILABLE_TIMES;
import static io.micrometer.common.util.StringUtils.isBlank;

@Embeddable
public class Detail {

    private static final int COUNT_DAYS_OF_WEEK = 7;
    public static final int MAP_URL_MAX_LENGTH = 512;
    public static final int PHONE_MAX_LENGTH = 20;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<AvailableTime> availableTimes;
    @Column(nullable = false)
    private String mapUrl;
    @Column(columnDefinition = "text")
    private String description;
    private String phone;

    protected Detail() {
    }

    public Detail(final List<AvailableTime> availableTimes, final String mapUrl, final String description,
                  final String phone) {
        validate(availableTimes, mapUrl, phone);
        this.availableTimes = availableTimes;
        this.mapUrl = mapUrl;
        this.description = description;
        this.phone = phone;
    }

    private void validate(final List<AvailableTime> availableTimes, final String mapUrl, final String phone) {
        validateAvailableTimes(availableTimes);
        if (isBlank(mapUrl) || mapUrl.length() > MAP_URL_MAX_LENGTH) {
            throw new BadRequestException(ErrorCode.INVALID_CAFE_MAP_URL);
        }
        if (phone.length() > PHONE_MAX_LENGTH) {
            throw new BadRequestException(ErrorCode.INVALID_CAFE_PHONE);
        }
    }

    private void validateAvailableTimes(final List<AvailableTime> availableTimes) {
        final long countUniques = availableTimes.stream()
                .map(AvailableTime::getDay)
                .distinct()
                .count();

        if (countUniques != availableTimes.size()) {
            throw new BadRequestException(DUPLICATED_CAFE_AVAILABLE_TIMES);
        }
        if (availableTimes.size() != COUNT_DAYS_OF_WEEK) {
            throw new BadRequestException(INVALID_CAFE_AVAILABLE_TIMES);
        }
    }

    public List<AvailableTime> getAvailableTimes() {
        return availableTimes;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone() {
        return phone;
    }
}
