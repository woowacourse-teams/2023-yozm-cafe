package com.project.yozmcafe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.project.yozmcafe.domain.resizedimage.ImageResizer;
import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.exception.ErrorCode;

class ImageResizerTest {

    @Nested
    @DisplayName("ImageResizer 생성 테스트")
    class ConstructTest {
        @Test
        @DisplayName("Content-Type이 null이면 예외가 발생한다")
        void construct_fail1() {
            //given
            final TestMultipartFile multipartFile = TestMultipartFile.getWithContentType(null);

            //when, then
            assertThatThrownBy(() -> new ImageResizer(multipartFile, "fileName.png"))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(ErrorCode.NOT_IMAGE.getMessage());
        }

        @Test
        @DisplayName("이미지가 아니면 예외가 발생한다")
        void construct_fail2() {
            //given
            final TestMultipartFile multipartFile = TestMultipartFile.getWithContentType("file");

            //when, then
            assertThatThrownBy(() -> new ImageResizer(multipartFile, "fileName.png"))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(ErrorCode.NOT_IMAGE.getMessage());
        }

        @Test
        @DisplayName("이미지가 사이즈가 10MB가 넘으면 예외가 발생한다")
        void construct_fail3() {
            //given
            final TestMultipartFile multipartFile = TestMultipartFile.getWithSize(1024 * 1024 * 10 + 1);

            //when, then
            assertThatThrownBy(() -> new ImageResizer(multipartFile, "fileName.png"))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(ErrorCode.INVALID_IMAGE_SIZE.getMessage());
        }

        @Test
        @DisplayName("예외가 발생하지 않는다")
        void construct() throws Exception {
            //given
            final File file = new File("src/test/resources/image.png");
            final FileInputStream fileInputStream = new FileInputStream(file);
            final MockMultipartFile image = new MockMultipartFile("image", "image.png", "image/png", fileInputStream);

            //when, then
            assertDoesNotThrow(() -> new ImageResizer(image, "fileName.png"));
        }
    }

    @Test
    @DisplayName("리사이즈된 이미지들을 리턴한다")
    void getResizedImages() throws Exception {
        //given
        final MultipartFile image = makeMultipartFile();
        final ImageResizer imageResizer = new ImageResizer(image, "fileName.png");

        //when
        final List<MultipartFile> results = imageResizer.resizeImageToAllSizes();
        final List<String> fileNameWithPathResult = results.stream()
                .map(MultipartFile::getOriginalFilename)
                .toList();

        //then
        assertThat(fileNameWithPathResult).containsExactlyInAnyOrder(
                "100/fileName.png",
                "500/fileName.png"
        );
    }

    private MultipartFile makeMultipartFile() throws IOException {
        final File file = new File("src/test/resources/image.png");
        final FileInputStream fileInputStream = new FileInputStream(file);
        return new MockMultipartFile("image", "image.png", "image/png", fileInputStream);
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
