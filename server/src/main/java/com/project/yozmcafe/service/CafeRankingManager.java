package com.project.yozmcafe.service;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.exception.BadRequestException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.project.yozmcafe.exception.ErrorCode.RANK_OUT_OF_BOUNDS;

@Component
public class CafeRankingManager {

    private static final int MAX_RANK = 30;

    public int getRank(final List<Cafe> cafes, final Cafe cafe, Pageable pageable) {
        validatePage(pageable);
        return (pageable.getPageSize() * pageable.getPageNumber()) + cafes.indexOf(cafe) + 1;
    }

    private void validatePage(final Pageable pageable) {
        if (MAX_RANK / pageable.getPageSize() <= pageable.getPageNumber()) {
            throw new BadRequestException(RANK_OUT_OF_BOUNDS);
        }
    }
}
