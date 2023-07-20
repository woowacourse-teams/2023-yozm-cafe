package com.project.yozmcafe.domain.cafe;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.fixture.Fixture;

class UnViewedCafeTest {

    private static final Member MEMBER = new Member(1L);
    private static final Cafe CAFE = Fixture.getCafe("카페1", "주소1", 3);
    private UnViewedCafe unViewedCafe;

    @BeforeEach
    void setUp() {
        unViewedCafe = new UnViewedCafe(CAFE, MEMBER);
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
        final Cafe cafe2 = Fixture.getCafe("카페1", "주소1", 3);
        return Stream.of(
                Arguments.of(MEMBER, CAFE, true),
                Arguments.of(MEMBER, cafe2, false),
                Arguments.of(new Member(2L), cafe2, false),
                Arguments.of(null, cafe2, false),
                Arguments.of(MEMBER, null, false)
        );
    }
}
