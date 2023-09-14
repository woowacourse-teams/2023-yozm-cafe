package com.project.yozmcafe.service;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.yozmcafe.controller.dto.cafe.CafeRequest;
import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.controller.dto.cafe.CafeUpdateRequest;
import com.project.yozmcafe.domain.ImageHandler;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.Images;
import com.project.yozmcafe.exception.BadRequestException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
@Transactional(readOnly = true)
public class CafeAdminService {

    private final ImageHandler imageHandler;
    private final CafeRepository cafeRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public CafeAdminService(final ImageHandler imageHandler,
                            final CafeRepository cafeRepository,
                            final EntityManager entityManager) {
        this.imageHandler = imageHandler;
        this.cafeRepository = cafeRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public Long save(final CafeRequest cafeRequest, final List<MultipartFile> images) {
        final List<String> uploadedImageNames = imageHandler.resizeAndUploadToAllSizes(images);
        final Cafe cafe = cafeRequest.toCafe(uploadedImageNames);
        return cafeRepository.save(cafe).getId();
    }

    @Transactional
    public void update(final long cafeId, final CafeUpdateRequest request, final List<MultipartFile> images) {
        final Cafe cafe = getOrThrow(cafeId);
        deleteCafeImages(cafe);

        final List<String> newImageNames = imageHandler.resizeAndUploadToAllSizes(images);
        final Cafe requestedCafe = request.toCafeWithId(cafeId, newImageNames);

        cafe.update(requestedCafe);
    }

    private void deleteCafeImages(final Cafe cafe) {
        final List<String> currentImages = cafe.getImages().getUrls();
        imageHandler.deleteAll(currentImages);
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
        final Cafe cafe = getOrThrow(cafeId);
        deleteCafeImages(cafe);

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
        final Images images = getOrThrow(cafeId).getImages();
        return images.getUrls();
    }
}
