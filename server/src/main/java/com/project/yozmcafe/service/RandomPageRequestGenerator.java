package com.project.yozmcafe.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomPageRequestGenerator {

    private static final int FIRST_PAGE = 0;
    private final SecureRandom secureRandom = new SecureRandom();

    public RandomPageRequestGenerator() {
    }

    public PageRequest getPageRequestWithCafeCount(final Long unViewedCafeCount, final int pageSize) {
        if (unViewedCafeCount <= pageSize) {
            return PageRequest.of(FIRST_PAGE, pageSize);
        }
        final int pageIdx = secureRandom.nextInt((int) Math.ceil((double) unViewedCafeCount / pageSize));
        return PageRequest.of(pageIdx, pageSize);
    }
}
