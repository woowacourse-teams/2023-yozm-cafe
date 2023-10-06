package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.domain.member.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import jakarta.transaction.Transactional;
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
    private static final int INSERT_BATCH_SIZE = 110;
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

    @Transactional
    public void saveUnViewedCafes(final List<Cafe> cafes, final Member member) {
        final List<Cafe> insertCafes = new ArrayList<>();
        for (int cafeIdx = 0; cafeIdx < cafes.size(); cafeIdx++) {
            insertCafes.add(cafes.get(cafeIdx));

            if (isReachBatchSize(cafeIdx)) {
                batchInsert(insertCafes, member.getId());
                insertCafes.clear();
            }
        }
        if (!insertCafes.isEmpty()) {
            batchInsert(insertCafes, member.getId());
        }
    }

    private boolean isReachBatchSize(final int cafeIdx) {
        return (cafeIdx + 1) % INSERT_BATCH_SIZE == 0;
    }

    private void batchInsert(final List<Cafe> cafes, final String memberId) {
        final String sql = "INSERT INTO un_viewed_cafe (cafe_id, member_id) VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement pstmt, int i) throws SQLException {
                        pstmt.setLong(1, cafes.get(i).getId());
                        pstmt.setString(2, memberId);
                    }

                    @Override
                    public int getBatchSize() {
                        return cafes.size();
                    }
                }
        );
    }
}
