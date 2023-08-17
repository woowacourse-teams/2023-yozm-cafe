package com.project.yozmcafe.domain;

import com.project.yozmcafe.domain.resizedimage.Size;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class SizeTest {

    @Test
    @DisplayName("파일이름에 모든 경로를 붙여 반환한다.")
    void addAllPathPrefix() {
        //given
        final String fileName = "image.png";

        //when
        final List<String> fileNames = Size.getFileNamesWithPath(fileName);

        //then
        assertSoftly(soft -> {
            assertThat(fileNames).hasSize(Size.values().length);
            assertThat(new HashSet<>(fileNames)).hasSize(fileNames.size());
        });
    }

    @ParameterizedTest
    @EnumSource(Size.class)
    @DisplayName("파일이름에 경로를 붙여 반환한다.")
    void addPathPrefix(final Size size) {
        //given
        final String fileName = "image.png";

        //when
        final String fileNameWithPath = size.getFileNameWithPath(fileName);

        //then
        assertThat(fileNameWithPath).isEqualTo(size.getPath() + fileName);
    }
}
