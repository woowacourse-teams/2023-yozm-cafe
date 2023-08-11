package com.project.yozmcafe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileNameGeneratorTest {


    @Test
    @DisplayName("리사이즈 경로를 포함한 파일 이름을 생성한다.")
    void generateFileNames() {
        //given
        String extension = ".png";
        String originalFileName = "image1" + extension;
        final FileNameGenerator fileNameGenerator = FileNameGenerator.from(originalFileName);

        //when
        String result = fileNameGenerator.getFileName();

        //then
        assertThat(result.endsWith(extension)).isTrue();
    }
}
