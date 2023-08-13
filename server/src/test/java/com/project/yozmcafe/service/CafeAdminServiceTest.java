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
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.exception.BadRequestException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.RollbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalTime;
import java.util.List;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:truncate.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CafeAdminServiceTest {

    @Autowired
    private CafeAdminService cafeAdminService;
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LikedCafeService likedCafeService;
    @Autowired
    private UnViewedCafeService unViewedCafeService;
    @Autowired
    private EntityManagerFactory emf;

    @Test
    @DisplayName("카페 생성 테스트")
    void construct() {
        //given
        final CafeRequest request = new CafeRequest("연어카페", "주소", detail());

        //when
        cafeAdminService.save(request, List.of("image1"));

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
            assertThatThrownBy(() -> cafeAdminService.update(cafeId, cafeUpdateRequest, List.of("image.png")))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(NOT_EXISTED_CAFE.getMessage());
        }

        @Test
        @DisplayName("이름 수정")
        void update1() {
            //given
            final Cafe cafe = saveCafe();
            final String nameForUpdate = "참치카페";
            final CafeUpdateRequest cafeUpdateRequest = new CafeUpdateRequest(nameForUpdate, cafe.getAddress(),
                    detailFrom(cafe), cafe.getLikeCount());

            //when
            cafeAdminService.update(cafe.getId(), cafeUpdateRequest, imagesFrom(cafe));

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
            final CafeUpdateRequest request = new CafeUpdateRequest(
                    cafe.getName(), addressForUpdate, detailFrom(cafe), cafe.getLikeCount());

            //when
            cafeAdminService.update(cafe.getId(), request, imagesFrom(cafe));

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
            final CafeUpdateRequest request = new CafeUpdateRequest(cafe.getName(), cafe.getAddress(), detailFrom(cafe),
                    cafe.getLikeCount());

            //when
            cafeAdminService.update(cafe.getId(), request, imagesForUpdate);

            //then
            final Cafe persisted = cafeRepository.findById(cafe.getId()).get();
            assertThat(persisted.getImages().getUrls())
                    .usingRecursiveComparison().isEqualTo(imagesForUpdate);
        }

        @Test
        @DisplayName("좋아요 수 수정")
        void update4() {
            //given
            final Cafe cafe = saveCafe();
            final int likeCountForUpdate = 10000;

            //when
            cafeAdminService.update(cafe.getId(), new CafeUpdateRequest(
                    cafe.getName(), cafe.getAddress(), detailFrom(cafe), likeCountForUpdate), imagesFrom(cafe));

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
        @DisplayName("다른 트랜잭션에서 LikedCafe, UnViewedCafe를 사용하고 있을 때 삭제 테스트")
        void delete_foreign() {
            //given
            memberRepository.save(new Member("id", "연어", "image"));
            final EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            final Cafe cafe = saveCafe();
            final Member member = em.find(Member.class, "id");
            unViewedCafeService.removeUnViewedCafeByCafeId(member, cafe.getId());
            likedCafeService.updateLike(member, cafe.getId(), true);

            //when
            cafeAdminService.delete(cafe.getId());

            //then
            assertThatThrownBy(() -> em.getTransaction().commit())
                    .isInstanceOf(RollbackException.class);
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
        return cafeRepository.save(new Cafe("연어카페", "주소", new Images(List.of("image")), detail().toDetail()));
    }

    private DetailRequest detail() {
        final AvailableTimeRequest time1 = new AvailableTimeRequest(Days.MONDAY, LocalTime.now(), LocalTime.now(),
                true);
        final AvailableTimeRequest time2 = new AvailableTimeRequest(Days.TUESDAY, LocalTime.now(), LocalTime.now(),
                true);
        final AvailableTimeRequest time3 = new AvailableTimeRequest(Days.WEDNESDAY, LocalTime.now(), LocalTime.now(),
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

    private List<String> imagesFrom(final Cafe cafe) {
        return cafe.getImages().getUrls();
    }
}
