package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.domain.member.Member;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UnViewedCafeRepository {

    private static final int INSERT_BATCH_SIZE = 110;

    private final JdbcTemplate jdbcTemplate;

    public UnViewedCafeRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void saveUnViewedCafes(final List<Cafe> cafes, final Member member) {
        for (int i = 0; i < cafes.size(); i += INSERT_BATCH_SIZE) {
            List<Cafe> subItems = cafes.subList(i, Math.min(i + INSERT_BATCH_SIZE, cafes.size()));
            batchInsert(subItems, member.getId());
        }
    }

    private void batchInsert(final List<Cafe> cafes, final String memberId) {
        final String sql = "INSERT INTO un_viewed_cafe (cafe_id, member_id) VALUES (?, ?)";

        final List<Object[]> batchArguments = cafes.stream()
                .map(cafe -> new Object[]{cafe.getId(), memberId})
                .toList();

        jdbcTemplate.batchUpdate(sql, batchArguments);
    }
}
