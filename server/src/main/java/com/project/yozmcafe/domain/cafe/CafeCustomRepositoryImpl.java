package com.project.yozmcafe.domain.cafe;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.yozmcafe.domain.cafe.QCafe.cafe;
import static com.project.yozmcafe.domain.menu.QMenu.menu;
import static com.querydsl.core.types.dsl.Expressions.numberTemplate;
import static io.micrometer.common.util.StringUtils.isBlank;

@Repository
public class CafeCustomRepositoryImpl extends QuerydslRepositorySupport implements CafeCustomRepository {

    private static final double MATCH_THRESHOLD = 0.0;
    private static final String MATCH_LITERALLY_OPERATOR = "\"";

    public CafeCustomRepositoryImpl() {
        super(Cafe.class);
    }

    public List<Cafe> findAllBy(final String cafeNameWord, final String menuWord, final String addressWord) {
        return from(cafe)
                .innerJoin(menu).on(menu.cafe.eq(cafe))
                .where(
                        contains(cafe.name, cafeNameWord),
                        contains(menu.name, menuWord),
                        contains(cafe.address, addressWord))
                .fetch();
    }

    public List<Cafe> findAllBy(final String cafeNameWord, final String addressWord) {
        return from(cafe)
                .where(
                        contains(cafe.name, cafeNameWord),
                        contains(cafe.address, addressWord))
                .fetch();
    }

    private BooleanExpression contains(final StringPath target, final String searchWord) {
        if (isBlank(searchWord)) {
            return null;
        }

        final String literalSearchWord = MATCH_LITERALLY_OPERATOR + searchWord + MATCH_LITERALLY_OPERATOR;

        return numberTemplate(Double.class, "function('match_against', {0}, {1})",
                target, literalSearchWord).gt(MATCH_THRESHOLD);
    }
}
