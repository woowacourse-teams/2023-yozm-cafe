package com.project.yozmcafe.domain.cafe.available;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

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
        this.day = day;
        this.open = open;
        this.close = close;
        this.isOpened = isOpened;
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
