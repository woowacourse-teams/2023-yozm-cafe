package com.project.yozmcafe.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.doNothing;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import com.project.yozmcafe.domain.S3Client;

@SpringBootTest
class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @MockBean
    private S3Client s3Client;

    @Test
    @DisplayName("파일 업로드 후 업로드된 파일의 이름들을 반환한다.")
    void upload() throws Exception {
        //given
        doNothing().when(s3Client).upload(any(MockMultipartFile.class));
        final File file = new File("src/test/resources/image.png");
        final FileInputStream fileInputStream = new FileInputStream(file);
        final MockMultipartFile image = new MockMultipartFile("image", "image.png", "image/png", fileInputStream);

        //when
        final List<String> result = imageService.upload(List.of(image));

        //then
        assertSoftly(softAssertions -> {
            assertThat(result).hasSize(1);
            assertThat(result.get(0).endsWith(".png")).isTrue();
        });
    }
}
