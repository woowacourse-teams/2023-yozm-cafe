package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.cafe.AvailableTimeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeUpdateRequest;
import com.project.yozmcafe.controller.dto.cafe.DetailRequest;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.Detail;
import com.project.yozmcafe.domain.cafe.Images;
import com.project.yozmcafe.domain.cafe.available.Days;
import com.project.yozmcafe.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class CafeAdminServiceTest {

    @Autowired
    private CafeAdminService cafeAdminService;
    @Autowired
    private CafeRepository cafeRepository;

    @Test
    @DisplayName("카페 생성 테스트")
    void construct() {
        //given
        final CafeRequest request = new CafeRequest("연어카페", "주소", images(), detail());

        //when
        cafeAdminService.save(request);

        //then
        assertThat(cafeRepository.findAll()).hasSize(1);
    }

    @Nested
    @DisplayName("카페 수정 테스트")
    class UpdateCafeTest {

        @Test
        @DisplayName("해당 카페가 존재하지 않으면 예외가 발생한다")
        void notExist() {
            assertThatThrownBy(() -> cafeAdminService.update(0L, new CafeUpdateRequest(
                    "name", "address", images(), detail(), 100)))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(NOT_EXISTED_CAFE.getMessage());
        }

        @Test
        @DisplayName("이름 수정")
        void update1() {
            //given
            final Cafe cafe = saveCafe();
            final String nameForUpdate = "참치카페";

            //when
            cafeAdminService.update(cafe.getId(), new CafeUpdateRequest(
                    nameForUpdate, cafe.getAddress(), imagesFrom(cafe), detailFrom(cafe), cafe.getLikeCount()));

            //then
            final Cafe persisted = cafeRepository.findById(cafe.getId()).get();
            assertThat(persisted.getName()).isEqualTo(nameForUpdate);
        }

        @Test
        @DisplayName("주소 수정")
        void update2() {
            //given
            final Cafe cafe = saveCafe();
            final String addressForUpdate = "해운대";

            //when
            cafeAdminService.update(cafe.getId(), new CafeUpdateRequest(
                    cafe.getName(), addressForUpdate, imagesFrom(cafe), detailFrom(cafe), cafe.getLikeCount()));

            //then
            final Cafe persisted = cafeRepository.findById(cafe.getId()).get();
            assertThat(persisted.getAddress()).isEqualTo(addressForUpdate);
        }

        @Test
        @DisplayName("이미지 수정")
        void update3() {
            //given
            final Cafe cafe = saveCafe();
            final List<String> imagesForUpdate = List.of("바뀐 이미지");

            //when
            cafeAdminService.update(cafe.getId(), new CafeUpdateRequest(
                    cafe.getName(), cafe.getAddress(), imagesForUpdate, detailFrom(cafe), cafe.getLikeCount()));

            //then
            final Cafe persisted = cafeRepository.findById(cafe.getId()).get();
            assertThat(persisted.getImages().getUrls()).isEqualTo(imagesForUpdate);
        }

        @Test
        @DisplayName("좋아요 수 수정")
        void update5() {
            //given
            final Cafe cafe = saveCafe();
            final int likeCountForUpdate = 10000;

            //when
            cafeAdminService.update(cafe.getId(), new CafeUpdateRequest(
                    cafe.getName(), cafe.getAddress(), imagesFrom(cafe), detailFrom(cafe), likeCountForUpdate));

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
            final Cafe cafe = saveCafe();

            //when
            cafeAdminService.delete(cafe.getId());

            //then
            assertThat(cafeRepository.findAll()).isEmpty();
        }

        @Test
        @DisplayName("LikedCafe, UnViewedCafe와 Foreign 제약이 있을 때 삭제")
        void delete_foreign() {
            //given
            final Cafe cafe = saveCafe();

            //when

            //then
        }
    }

    private List<String> images() {
        return List.of("이미지1", "이미지2");
    }

    private DetailRequest detail() {
        final AvailableTimeRequest time1 = new AvailableTimeRequest(Days.MONDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTimeRequest time2 = new AvailableTimeRequest(Days.TUESDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTimeRequest time3 = new AvailableTimeRequest(Days.WEDNESDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTimeRequest time4 = new AvailableTimeRequest(Days.THURSDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTimeRequest time5 = new AvailableTimeRequest(Days.FRIDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTimeRequest time6 = new AvailableTimeRequest(Days.SATURDAY, LocalTime.now(), LocalTime.now(), true);
        final AvailableTimeRequest time7 = new AvailableTimeRequest(Days.SUNDAY, LocalTime.now(), LocalTime.now(), true);

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

    private List<String> imagesFrom(final Cafe cafe) {
        return cafe.getImages().getUrls();
    }

    private Cafe saveCafe() {
        return cafeRepository.save(new Cafe("연어카페", "주소", new Images(images()), detail().toDetail()));
    }
}
