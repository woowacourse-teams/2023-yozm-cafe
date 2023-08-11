package com.project.yozmcafe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileNameParserTest {

    private final FileNameParser fileNameParser = new FileNameParser();

    @Test
    @DisplayName("리사이즈 경로를 포함한 파일 이름을 생성한다.")
    void generateFileNames() {
        //given
        String extension = ".png";
        String originalFileName = "image1" + extension;

        //when
        final List<String> results = fileNameParser.generateFileNames(originalFileName);
        final boolean extensionResult = results.stream()
                .anyMatch(result -> !result.endsWith(extension));

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThat(results).hasSize(3);
            assertThat(extensionResult).isFalse();
        });
    }
}
