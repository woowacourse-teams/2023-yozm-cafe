package com.project.yozmcafe.service;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.project.yozmcafe.controller.dto.cafe.CafeCoordinateRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeUpdateRequest;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.Images;
import com.project.yozmcafe.domain.cafe.coordinate.CafeCoordinate;
import com.project.yozmcafe.domain.cafe.coordinate.CafeCoordinateRepository;
import com.project.yozmcafe.exception.BadRequestException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
@Transactional(readOnly = true)
public class CafeAdminService {

    private final CafeRepository cafeRepository;
    private final CafeCoordinateRepository cafeCoordinateRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public CafeAdminService(final CafeRepository cafeRepository,
                            final CafeCoordinateRepository cafeCoordinateRepository,
                            final EntityManager entityManager) {
        this.cafeRepository = cafeRepository;
        this.cafeCoordinateRepository = cafeCoordinateRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public Long save(final CafeRequest cafeRequest, List<String> imageNames) {
        final Cafe cafe = cafeRequest.toCafe(imageNames);
        return cafeRepository.save(cafe).getId();
    }

    @Transactional
    public void update(final long cafeId, final CafeUpdateRequest request, List<String> images) {
        final Cafe cafe = getCafeOrThrow(cafeId);
        final Cafe requestedCafe = request.toCafeWithId(cafeId, images);

        cafe.update(requestedCafe);
    }

    private Cafe getCafeOrThrow(final long cafeId) {
        return cafeRepository.findById(cafeId)
                .orElseThrow(() -> new BadRequestException(NOT_EXISTED_CAFE));
    }

    public List<CafeResponse> findAll() {
        return cafeRepository.findAll().stream()
                .map(CafeResponse::fromUnLoggedInUser)
                .toList();
    }

    public CafeResponse findById(final long cafeId) {
        final Cafe cafe = getCafeOrThrow(cafeId);
        return CafeResponse.fromUnLoggedInUser(cafe);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(final long cafeId) {
        Stream.of(
                        entityManager.createQuery("DELETE FROM UnViewedCafe u WHERE u.cafe.id = :cafeId"),
                        entityManager.createQuery("DELETE FROM LikedCafe l WHERE l.cafe.id = :cafeId"),
                        entityManager.createQuery("DELETE FROM MenuBoard mb WHERE mb.cafe.id = :cafeId"),
                        entityManager.createQuery("DELETE FROM Menu me WHERE me.cafe.id = :cafeId"),
                        entityManager.createQuery("DELETE FROM Cafe c WHERE c.id = :cafeId"))
                .map(query -> query.setParameter("cafeId", cafeId))
                .forEach(Query::executeUpdate);
    }

    public List<String> findImagesByCafeId(final Long cafeId) {
        final Images images = getCafeOrThrow(cafeId).getImages();
        return images.getUrls();
    }

    @Transactional
    public Long saveCafeCoordinate(final Long cafeId, final CafeCoordinateRequest cafeCoordinateRequest) {
        final Cafe cafe = getCafeOrThrow(cafeId);
        final CafeCoordinate cafeCoordinate = cafeCoordinateRequest.toCafeCoordinateWithCafe(cafe);
        cafeCoordinateRepository.save(cafeCoordinate);
        return cafeCoordinate.getId();
    }
}
