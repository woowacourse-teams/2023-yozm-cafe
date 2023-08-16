package com.project.yozmcafe.domain;

import com.project.yozmcafe.exception.BadRequestException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static com.project.yozmcafe.exception.ErrorCode.RANK_OUT_OF_BOUNDS;

@Component
public class CafeRankGenerator {

    public static final int MAX_RANK = 30;

    public int makeRank(final int index, final Pageable pageable) {
        validatePage(pageable);
        return (pageable.getPageSize() * pageable.getPageNumber()) + index + 1;
    }

    public void validatePage(final Pageable pageable) {
        final int maxPage = MAX_RANK / pageable.getPageSize();
        if (maxPage <= pageable.getPageNumber()) {
            throw new BadRequestException(RANK_OUT_OF_BOUNDS);
        }
    }
}
