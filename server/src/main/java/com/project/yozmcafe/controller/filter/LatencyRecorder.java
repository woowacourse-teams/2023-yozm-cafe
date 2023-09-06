package com.project.yozmcafe.controller.filter;

import org.springframework.stereotype.Component;

@Component
public class LatencyRecorder {

    private final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public void start() {
        threadLocal.set(System.currentTimeMillis());
    }

    public double getDurationSeconds() {
        final long start = threadLocal.get();
        final long end = System.currentTimeMillis();

        threadLocal.remove();
        final double millis = (double) end - start;
        return millis / 1000;
    }
}
