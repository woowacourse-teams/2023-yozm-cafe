package com.project.yozmcafe.domain.cafe;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.yozmcafe.exception.BadRequestException;

class ImagesTest {

    @Test
    @DisplayName("대표 이미지를 가져온다.")
    void getRepresentativeImage() {
        //given
        final List<String> urls = List.of("오션.img", "연어.img", "요즘카페.img");
        final Images images = new Images(urls);

        //when
        final String representativeImage = images.getRepresentativeImage();

        //then
        assertThat(representativeImage).isEqualTo("오션.img");
    }

    @Test
    @DisplayName("대표 이미지를 조회하는데 이미지가 없을 경우 예외가 발생한다.")
    void getRepresentativeImage_fail() {
        //given
        final Images images = new Images(Collections.emptyList());

        //when
        //then
        assertThatThrownBy(images::getRepresentativeImage)
                .isInstanceOf(BadRequestException.class)
                .hasMessage(NOT_EXISTED_CAFE_IMAGE.getMessage());
    }
}
