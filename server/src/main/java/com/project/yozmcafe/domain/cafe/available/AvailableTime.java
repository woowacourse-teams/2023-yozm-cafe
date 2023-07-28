package com.project.yozmcafe.domain.cafe.available;

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

    @Column(name = "is_opened")
    private boolean opened;

    protected AvailableTime() {
    }

    public AvailableTime(final Days day, final LocalTime open, final LocalTime close, final boolean opened) {
        this.day = day;
        this.open = open;
        this.close = close;
        this.opened = opened;
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

    public boolean getOpened() {
        return opened;
    }
}
