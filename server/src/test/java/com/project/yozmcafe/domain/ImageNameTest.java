package com.project.yozmcafe.domain;

import com.project.yozmcafe.domain.resizedimage.ImageName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ImageNameTest {

    @Test
    @DisplayName("기존 이미지 이름을 날짜 형식의 이름으로 변환한다")
    void construct() {
        //given
        final String extension = ".png";
        final String fileName = "image1";
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS");

        //when
        final ImageName imageName = ImageName.from(fileName + extension);

        //then
        final String removedExtension = imageName.get().replace(extension, "");
        assertDoesNotThrow(() -> formatter.parse(removedExtension));
    }
}
