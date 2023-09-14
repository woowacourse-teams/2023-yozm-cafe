
package com.project.yozmcafe.domain.cafe;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.querydsl.jpa.JPAExpressions.*;
import static io.micrometer.common.util.StringUtils.isBlank;

import static com.project.yozmcafe.domain.cafe.QCafe.cafe;
import static com.project.yozmcafe.domain.menu.QMenu.menu;

import java.util.List;


@Repository
public class CafeCustomRepositoryImpl extends QuerydslRepositorySupport implements CafeCustomRepository {

    public CafeCustomRepositoryImpl() {
        super(Cafe.class);
    }

    public List<Cafe> findAllBy(final String cafeNameWord, final String menuWord, final String addressWord) {
        return from(cafe)
                .leftJoin(menu).on(menu.cafe.eq(cafe))
                .innerJoin(cafe.images.urls).fetchJoin()
                .where(
                        containsMenu(menuWord),
                        contains(cafe.name, cafeNameWord),
                        contains(cafe.address, addressWord))
                .fetch();
    }

    public List<Cafe> findAllBy(final String cafeNameWord, final String addressWord) {
        return from(cafe)
                .innerJoin(cafe.images.urls).fetchJoin()
                .where(
                        contains(cafe.name, cafeNameWord),
                        contains(cafe.address, addressWord))
                .fetch();
    }

    private BooleanExpression containsMenu(final String searchWord) {
        if (isBlank(searchWord)) {
            return null;
        }

        return cafe.id.in(
                select(menu.cafe.id)
                        .from(menu)
                        .where(menu.name.containsIgnoreCase(searchWord))
        );
    }

    private BooleanExpression contains(final StringPath target, final String string) {
        if (isBlank(string)) {
            return null;
        }

        return target.containsIgnoreCase(string);
    }
}
