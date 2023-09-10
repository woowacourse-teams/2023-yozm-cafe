
package com.project.yozmcafe.domain.cafe;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.StringUtils.hasText;

import static com.project.yozmcafe.domain.cafe.QCafe.cafe;
import static com.project.yozmcafe.domain.menu.QMenu.menu;

import java.util.Collections;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class CafeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CafeQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<Cafe> searchCafesByWord(final String searchWord, final boolean isCafeName, final boolean isMenu, final boolean isAddress) {
        if (!hasText(searchWord)) {
            return Collections.emptyList();
        }

        final BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (!isCafeName && !isMenu && !isAddress) {
            return queryFactory.selectFrom(cafe)
                    .leftJoin(menu).on(menu.cafe.eq(cafe))
                    .where(booleanBuilder.or(containsCafeName(true, searchWord))
                            .or(containsAddress(true, searchWord))
                            .or(containsMenu(true, searchWord)))
                    .fetch();
        }

        final JPAQuery<Cafe> baseQuery = queryFactory.selectFrom(cafe)
                .where(booleanBuilder.and(containsCafeName(isCafeName, searchWord))
                        .and(containsAddress(isAddress, searchWord))
                        .and(containsMenu(isMenu, searchWord)));

        if (isMenu && !isCafeName && !isAddress) {
            return baseQuery
                    .innerJoin(menu).on(menu.cafe.eq(cafe))
                    .fetch();
        }

        if (isMenu) {
            return baseQuery
                    .leftJoin(menu).on(menu.cafe.eq(cafe))
                    .fetch();
        }

        return baseQuery.fetch();
    }

    private BooleanExpression containsCafeName(final boolean isCafeName, final String searchWord) {
        if (!isCafeName) return null;
        return cafe.name.containsIgnoreCase(searchWord);
    }

    private BooleanExpression containsMenu(final boolean isMenu, final String searchWord) {
        if (!isMenu) return null;
        return menu.name.containsIgnoreCase(searchWord);
    }

    private BooleanExpression containsAddress(final boolean isAddress, final String searchWord) {
        if (!isAddress) return null;
        return cafe.address.containsIgnoreCase(searchWord);
    }
}
