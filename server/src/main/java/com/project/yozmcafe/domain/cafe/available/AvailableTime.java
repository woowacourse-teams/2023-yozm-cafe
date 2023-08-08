package com.project.yozmcafe.domain.cafe.available;

import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalTime;

@Embeddable
public class AvailableTime {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Days day;
    private LocalTime open;
    private LocalTime close;
    private boolean isOpened;

    protected AvailableTime() {
    }

    public AvailableTime(final Days day, final LocalTime open, final LocalTime close, final boolean isOpened) {
        validate(open, close);
        this.day = day;
        this.open = open;
        this.close = close;
        this.isOpened = isOpened;
    }

    private void validate(final LocalTime open, final LocalTime close) {
        if (close.isBefore(open)) {
            throw new BadRequestException(ErrorCode.INVALID_CAFE_AVAILABLE_TIME);
        }
    }

    public Days getDay() {
        return day;
    }

    public LocalTime getOpen() {
        return open;
    }

    public LocalTime getClose() {
        return close;
    }

    public boolean isOpened() {
        return isOpened;
    }
}
