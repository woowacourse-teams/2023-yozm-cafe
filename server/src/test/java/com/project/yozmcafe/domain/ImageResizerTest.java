package com.project.yozmcafe.domain;

import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ImageResizerTest {

    @Test
    @DisplayName("ImageResizer 생성시 Content-Type이 null이면 예외가 발생한다")
    void construct_fail1() {
        //given
        final TestMultipartFile multipartFile = TestMultipartFile.getWithContentType(null);

        //when, then
        assertThatThrownBy(() -> new ImageResizer(multipartFile))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ErrorCode.NOT_IMAGE.getMessage());
    }

    @Test
    @DisplayName("ImageResizer 생성시 이미지가 아니면 예외가 발생한다")
    void construct_fail2() {
        //given
        final TestMultipartFile multipartFile = TestMultipartFile.getWithContentType("file");

        //when, then
        assertThatThrownBy(() -> new ImageResizer(multipartFile))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ErrorCode.NOT_IMAGE.getMessage());
    }

    @Test
    @DisplayName("ImageResizer 생성시 이미지가 사이즈가 5MB가 넘으면 예외가 발생한다")
    void construct_fail3() {
        //given
        final TestMultipartFile multipartFile = TestMultipartFile.getWithSize(1024 * 1024 * 5 + 1);

        //when, then
        assertThatThrownBy(() -> new ImageResizer(multipartFile))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ErrorCode.INVALID_IMAGE_SIZE.getMessage());
    }

    @Test
    @DisplayName("생성 테스트")
    void contruct() {
        //given
        final TestMultipartFile multipartFile = TestMultipartFile.getWithSize(100);

        //when, then
        assertDoesNotThrow(() -> new ImageResizer(multipartFile));
    }

    @Test
    @DisplayName("리사이즈 테스트")
    void resize() throws Exception {
        //given
        final File file = new File("src/test/resources/image.png");
        final FileInputStream fileInputStream = new FileInputStream(file);
        final MockMultipartFile image = new MockMultipartFile("image", "image.png", "image/png", fileInputStream);

        //when, then
        assertDoesNotThrow(() -> new ImageResizer(image).resize(100));
    }

    private record TestMultipartFile(String contentType, int size) implements MultipartFile {

        public static TestMultipartFile getWithContentType(final String contentType) {
            return new TestMultipartFile(contentType, 1);
        }

        public static TestMultipartFile getWithSize(final int size) {
            return new TestMultipartFile("image/", size);
        }

        @Override
        public String getName() {
            return "name";
        }

        @Override
        public String getOriginalFilename() {
            return "name";
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return size;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return new byte[0];
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return null;
        }

        @Override
        public void transferTo(final File dest) throws IOException, IllegalStateException {
        }
    }

}
