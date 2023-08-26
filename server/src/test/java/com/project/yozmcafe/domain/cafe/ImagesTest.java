package com.project.yozmcafe.domain.cafe;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
