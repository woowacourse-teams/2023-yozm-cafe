package com.project.yozmcafe.service;


import static com.project.yozmcafe.domain.resizedimage.Size.MOBILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.project.yozmcafe.BaseTest;
import com.project.yozmcafe.domain.ImageHandler;
import com.project.yozmcafe.domain.S3Client;
import com.project.yozmcafe.domain.resizedimage.Size;

class ImageHandlerTest extends BaseTest {

    @Autowired
    private ImageHandler imageHandler;

    @MockBean
    private S3Client s3Client;

    @Test
    @DisplayName("리사이즈 이후 업로드 한다")
    void resizeAndUpload1() {
        //given
        final MockMultipartFile image = makeMultipartFile();
        final List<MultipartFile> images = List.of(image, image, image);

        //when
        final List<String> names = imageHandler.resizeAndUploadToAllSizes(images);

        //then
        final int expectedUploadCount = images.size() * Size.values().length;
        assertSoftly(soft -> {
            verify(s3Client, times(expectedUploadCount)).upload(any());
            assertThat(names).hasSameSizeAs(images);
        });
    }

    @Test
    @DisplayName("리사이즈 이후 업로드한다")
    void resizeAndUpload2() {
        //given
        final MockMultipartFile file = makeMultipartFile();

        //when
        imageHandler.resizeAndUploadToFixedSize(file, MOBILE);

        //then
        verify(s3Client, times(1)).upload(any());

    }

    @Test
    @DisplayName("파일 전체 삭제하면 모든 사이즈의 파일을 삭제한다")
    void deleteAll() {
        //when
        imageHandler.deleteAll(List.of("이미지"));

        //then
        verify(s3Client, times(Size.values().length)).delete(anyString());
    }

    private MockMultipartFile makeMultipartFile() {
        try {
            final File file = new File("src/test/resources/image.png");
            final FileInputStream fileInputStream = new FileInputStream(file);

            return new MockMultipartFile("image", "image.png", "image/png", fileInputStream);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
