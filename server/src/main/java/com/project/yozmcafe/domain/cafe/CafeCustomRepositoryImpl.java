package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.domain.member.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.project.yozmcafe.domain.cafe.QCafe.cafe;
import static com.project.yozmcafe.domain.menu.QMenu.menu;
import static com.querydsl.core.types.dsl.Expressions.numberTemplate;
import static io.micrometer.common.util.StringUtils.isBlank;

@Repository
public class CafeCustomRepositoryImpl extends QuerydslRepositorySupport implements CafeCustomRepository {

    private static final double MATCH_THRESHOLD = 0.0;
    private final JdbcTemplate jdbcTemplate;

    public CafeCustomRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        super(Cafe.class);
        this.jdbcTemplate = jdbcTemplate;
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

        final String formattedSearchWord = "\"" + searchWord + "\"";
        return numberTemplate(Double.class, "function('match_against', {0}, {1})",
                target, formattedSearchWord)
                .gt(MATCH_THRESHOLD);
    }

    public void saveUnViewedCafes(final List<Cafe> cafes, final Member member) {
        int batchSize = 100;
        int batchCount = 0;
        List<Cafe> subItems = new ArrayList<>();
        for (int i = 0; i < cafes.size(); i++) {
            subItems.add(cafes.get(i));
            if ((i + 1) % batchSize == 0) {
                batchCount = batchInsert(batchCount, subItems, member.getId());
            }
        }
        if (!subItems.isEmpty()) {
            batchCount = batchInsert(batchCount, subItems, member.getId());
        }
    }

    private int batchInsert(int batchCount, List<Cafe> subItems, String memberId) {
        String sql = "INSERT INTO un_viewed_cafe (cafe_id, member_id) VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement pstmt, int i) throws SQLException {
                        pstmt.setLong(1, subItems.get(i).getId());
                        pstmt.setString(2, memberId);
                    }

                    @Override
                    public int getBatchSize() {
                        return subItems.size();
                    }
                }
        );

        subItems.clear();
        batchCount++;
        return batchCount;
    }
}
