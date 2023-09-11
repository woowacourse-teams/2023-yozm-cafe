package com.project.yozmcafe.service;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.project.yozmcafe.BaseTest;
import com.project.yozmcafe.controller.dto.cafe.AvailableTimeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeUpdateRequest;
import com.project.yozmcafe.controller.dto.cafe.DetailRequest;
import com.project.yozmcafe.domain.ImageHandler;
import com.project.yozmcafe.domain.S3Client;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.Detail;
import com.project.yozmcafe.domain.cafe.Images;
import com.project.yozmcafe.domain.cafe.available.Days;
import com.project.yozmcafe.exception.BadRequestException;

class CafeAdminServiceTest extends BaseTest {

    private static MultipartFile imageFile;

    @Autowired
    private CafeAdminService cafeAdminService;
    @Autowired
    private CafeRepository cafeRepository;
    @SpyBean
    private S3Client s3Client;
    @SpyBean
    private ImageHandler imageHandler;

    @BeforeAll
    public static void beforeAll() throws IOException {
        final File file = new File("src/test/resources/image.png");
        final FileInputStream fileInputStream = new FileInputStream(file);
        imageFile = new MockMultipartFile("image", "image.png", "image/png", fileInputStream);
    }

    @Test
    @DisplayName("카페 생성 테스트")
    void construct() {
        //given
        doNothing().when(s3Client).upload(any());
        final CafeRequest request = new CafeRequest("연어카페", "주소", detail());

        //when
        cafeAdminService.save(request, List.of(imageFile, imageFile));

        //then
        assertThat(cafeRepository.findAll()).hasSize(1);
    }

    @Nested
    @DisplayName("카페 수정 테스트")
    class UpdateCafeTest {

        @Test
        @DisplayName("해당 카페가 존재하지 않으면 예외가 발생한다")
        void notExist() {
            final Long cafeId = 0L;
            final CafeUpdateRequest cafeUpdateRequest = new CafeUpdateRequest("name", "address", detail(), 100);
            final List<MultipartFile> imageFiles = List.of(imageFile, imageFile);
            assertThatThrownBy(() -> cafeAdminService.update(cafeId, cafeUpdateRequest, imageFiles))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(NOT_EXISTED_CAFE.getMessage());
        }

        @Test
        @DisplayName("이름 수정")
        void update1() {
            //given
            doNothing().when(s3Client).upload(any());
            doNothing().when(s3Client).delete(any());

            final Cafe cafe = saveCafe();
            final String nameForUpdate = "참치카페";
            final CafeUpdateRequest cafeUpdateRequest = new CafeUpdateRequest(nameForUpdate, cafe.getAddress(),
                    detailFrom(cafe), cafe.getLikeCount());

            //when
            cafeAdminService.update(cafe.getId(), cafeUpdateRequest, List.of(imageFile));

            //then
            final Cafe persisted = cafeRepository.findById(cafe.getId()).get();
            assertThat(persisted.getName()).isEqualTo(nameForUpdate);
        }

        @Test
        @DisplayName("주소 수정")
        void update2() {
            //given
            doNothing().when(s3Client).upload(any());
            doNothing().when(s3Client).delete(any());
            final Cafe cafe = saveCafe();
            final String addressForUpdate = "해운대";
            final CafeUpdateRequest request = new CafeUpdateRequest(
                    cafe.getName(), addressForUpdate, detailFrom(cafe), cafe.getLikeCount());

            //when
            cafeAdminService.update(cafe.getId(), request, List.of(imageFile));

            //then
            final Cafe persisted = cafeRepository.findById(cafe.getId()).get();
            assertThat(persisted.getAddress()).isEqualTo(addressForUpdate);
        }

        @Test
        @DisplayName("이미지 수정")
        void update3() {
            //given
            doNothing().when(s3Client).upload(any());
            doNothing().when(s3Client).delete(any());
            final List<String> imagesForUpdate = List.of("changedImage1", "changedImage2");
            doReturn(imagesForUpdate).when(imageHandler).resizeAndUploadToAllSizes(any());

            final Cafe cafe = saveCafe();
            final CafeUpdateRequest request = new CafeUpdateRequest(cafe.getName(), cafe.getAddress(),
                    detailFrom(cafe),
                    cafe.getLikeCount());

            //when
            cafeAdminService.update(cafe.getId(), request, List.of(imageFile, imageFile));

            //then
            final Cafe persisted = cafeRepository.findById(cafe.getId()).get();
            assertThat(persisted.getImages().getUrls())
                    .usingRecursiveComparison().isEqualTo(imagesForUpdate);
        }

        @Test
        @DisplayName("좋아요 수 수정")
        void update4() {
            //given
            doNothing().when(s3Client).upload(any());
            doNothing().when(s3Client).delete(any());
            final Cafe cafe = saveCafe();
            final int likeCountForUpdate = 10000;

            //when
            final CafeUpdateRequest request = new CafeUpdateRequest(cafe.getName(), cafe.getAddress(), detailFrom(cafe),
                    likeCountForUpdate);
            cafeAdminService.update(cafe.getId(), request, List.of(imageFile, imageFile));

            //then
            final Cafe persisted = cafeRepository.findById(cafe.getId()).get();
            assertThat(persisted.getLikeCount()).isEqualTo(likeCountForUpdate);
        }
    }

    @Test
    @DisplayName("전체 조회 테스트")
    void findAll() {
        //given
        saveCafe();
        saveCafe();
        saveCafe();

        //when
        final List<CafeResponse> persisted = cafeAdminService.findAll();

        //then
        assertThat(persisted).hasSize(3);
    }

    @Test
    @DisplayName("단건 조회 테스트 - 해당 카페가 존재하지 않으면 예외가 발생한다")
    void findById() {
        assertThatThrownBy(() -> cafeAdminService.findById(0L))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(NOT_EXISTED_CAFE.getMessage());
    }

    @Nested
    @DisplayName("삭제 테스트")
    class DeleteTest {

        @Test
        @DisplayName("단건 삭제")
        void delete() {
            //given
            doNothing().when(s3Client).delete(any());
            final Cafe cafe = saveCafe();

            //when
            cafeAdminService.delete(cafe.getId());

            //then
            assertThat(cafeRepository.findAll()).isEmpty();
        }
    }

    @Test
    @DisplayName("해당 카페의 사진들을 리턴한다")
    void findImagesByCafeId() {
        //given
        final Cafe cafe = saveCafe();

        //when
        final List<String> imagesByCafeId = cafeAdminService.findImagesByCafeId(cafe.getId());

        //then
        assertThat(imagesByCafeId).containsExactly("image");
    }

    private Cafe saveCafe() {
        doNothing().when(s3Client).upload(any());
        return cafeRepository.save(new Cafe("연어카페", "주소", new Images(List.of("image")), detail().toDetail()));
    }

    private DetailRequest detail() {
        final AvailableTimeRequest time1 = new AvailableTimeRequest(Days.MONDAY, LocalTime.now(), LocalTime.now(),
                true);
        final AvailableTimeRequest time2 = new AvailableTimeRequest(Days.TUESDAY, LocalTime.now(), LocalTime.now(),
                true);
        final AvailableTimeRequest time3 = new AvailableTimeRequest(Days.WEDNESDAY, LocalTime.now(),
                LocalTime.now(),
                true);
        final AvailableTimeRequest time4 = new AvailableTimeRequest(Days.THURSDAY, LocalTime.now(), LocalTime.now(),
                true);
        final AvailableTimeRequest time5 = new AvailableTimeRequest(Days.FRIDAY, LocalTime.now(), LocalTime.now(),
                true);
        final AvailableTimeRequest time6 = new AvailableTimeRequest(Days.SATURDAY, LocalTime.now(), LocalTime.now(),
                true);
        final AvailableTimeRequest time7 = new AvailableTimeRequest(Days.SUNDAY, LocalTime.now(), LocalTime.now(),
                true);

        return new DetailRequest(List.of(time1, time2, time3, time4, time5, time6, time7),
                "mapUrl", "description", "phone");
    }

    private DetailRequest detailFrom(final Cafe cafe) {
        final Detail detail = cafe.getDetail();

        final List<AvailableTimeRequest> availableTimes = detail.getAvailableTimes().stream()
                .map(availableTime -> new AvailableTimeRequest(availableTime.getDay(),
                        availableTime.getOpen(),
                        availableTime.getClose(),
                        availableTime.isOpened()))
                .toList();

        return new DetailRequest(availableTimes, detail.getMapUrl(), detail.getDescription(), detail.getPhone());
    }
}
