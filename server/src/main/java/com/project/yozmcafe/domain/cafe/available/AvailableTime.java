package com.project.yozmcafe.domain.cafe.available;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class AvailableTime {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Days day;
    private LocalDateTime open;
    private LocalDateTime close;
    private boolean isOpened;

    protected AvailableTime() {
    }

    public AvailableTime(final Days day, final LocalDateTime open, final LocalDateTime close, final boolean isOpened) {
        this.day = day;
        this.open = open;
        this.close = close;
        this.isOpened = isOpened;
    }
}
