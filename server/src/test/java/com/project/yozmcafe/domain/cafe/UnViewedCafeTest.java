package com.project.yozmcafe.domain.cafe;

import static com.project.yozmcafe.fixture.Fixture.CAFE_1;
import static com.project.yozmcafe.fixture.Fixture.CAFE_2;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.project.yozmcafe.domain.member.Member;

class UnViewedCafeTest {

    private static final Member MEMBER = new Member(1L);
    private UnViewedCafe unViewedCafe;

    @BeforeEach
    void setUp() {
        unViewedCafe = new UnViewedCafe(CAFE_1, MEMBER);
    }

    @MethodSource("provideExpectAndParams")
    @DisplayName("멤버와 카페가 일치하면 true를 반환한다. 일치하지 않으면 false를 반환한다.")
    @ParameterizedTest
    void isMatch(Member member, Cafe cafe, boolean expect) {
        final boolean result = unViewedCafe.isMatch(member, cafe);

        //then
        assertThat(result).isEqualTo(expect);
    }

    public static Stream<Arguments> provideExpectAndParams() {
        return Stream.of(
                Arguments.of(MEMBER, CAFE_1, true),
                Arguments.of(MEMBER, CAFE_2, false),
                Arguments.of(new Member(2L), CAFE_2, false),
                Arguments.of(null, CAFE_2, false),
                Arguments.of(MEMBER, null, false)
        );
    }
}
