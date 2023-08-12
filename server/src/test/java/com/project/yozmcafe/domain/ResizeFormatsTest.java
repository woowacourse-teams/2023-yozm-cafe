package com.project.yozmcafe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ResizeFormatsTest {
    @Test
    @DisplayName("파일이름에 모든 경로를 붙여 반환한다.")
    void addAllPathPrefix() {
        //given
        final String fileName = "image.png";

        //when
        final List<String> result = ResizeFormats.addAllPathPrefix(fileName);

        //then
        assertSoftly(softAssertions -> {
            assertThat(result).hasSize(3);
            assertThat(result).containsExactlyInAnyOrder("original/image.png", "100/image.png", "500/image.png");
        });
    }

    @ParameterizedTest
    @EnumSource(ResizeFormats.class)
    @DisplayName("파일이름에 경로를 붙여 반환한다.")
    void addPathPrefix(ResizeFormats resizeFormats) {
        //given
        final String fileName = "image.png";

        //when
        final String result = resizeFormats.addPathPrefix(fileName);

        //then
        assertThat(result).isEqualTo(resizeFormats.getPath() + fileName);
    }
}
