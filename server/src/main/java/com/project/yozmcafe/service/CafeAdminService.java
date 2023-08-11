package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.cafe.CafeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeUpdateRequest;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.Images;
import com.project.yozmcafe.exception.BadRequestException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE;

@Service
@Transactional(readOnly = true)
public class CafeAdminService {

    private final CafeRepository cafeRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public CafeAdminService(final CafeRepository cafeRepository, final EntityManager entityManager) {
        this.cafeRepository = cafeRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public Long save(final CafeRequest request, final List<String> images) {
        final Cafe cafe = new Cafe(request.name(), request.address(), new Images(images), request.getDetail());

        return cafeRepository.save(cafe).getId();
    }


    public static void main(final String[] args) {
        System.out.println(LocalDateTime.now());
    }

    //이미지 삭제용 API 하나 (카페 id, 이미지 경로)
    //이미지 추가용 API 하나 (카페 id, MultiPartFile)
    //UpdateRequest에서는 이미지 관련 처리 X
    @Transactional
    public void update(final long cafeId, final CafeUpdateRequest cafeRequest) {
        final Cafe cafe = getOrThrow(cafeId);
        final Cafe requestedCafe = cafeRequest.toCafeWithId(cafeId);
        cafe.update(requestedCafe);
    }

    private Cafe getOrThrow(final long cafeId) {
        return cafeRepository.findById(cafeId)
                .orElseThrow(() -> new BadRequestException(NOT_EXISTED_CAFE));
    }

    public List<CafeResponse> findAll() {
        return cafeRepository.findAll().stream()
                .map(CafeResponse::fromUnLoggedInUser)
                .toList();
    }

    public CafeResponse findById(final long cafeId) {
        final Cafe cafe = getOrThrow(cafeId);
        return CafeResponse.fromUnLoggedInUser(cafe);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(final long cafeId) {
        Stream.of(
                        entityManager.createQuery("DELETE FROM UnViewedCafe u WHERE u.cafe.id = :cafeId"),
                        entityManager.createQuery("DELETE FROM LikedCafe l WHERE l.cafe.id = :cafeId"),
                        entityManager.createQuery("DELETE FROM Cafe c WHERE c.id = :cafeId"))
                .map(query -> query.setParameter("cafeId", cafeId))
                .forEach(Query::executeUpdate);
    }
}
