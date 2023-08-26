package com.project.yozmcafe.domain.cafe;

import com.project.yozmcafe.exception.BadRequestException;
import jakarta.persistence.*;

import java.util.Objects;

import static com.project.yozmcafe.exception.ErrorCode.INVALID_CAFE_ADDRESS;
import static com.project.yozmcafe.exception.ErrorCode.INVALID_CAFE_LIKE_COUNT;
import static com.project.yozmcafe.exception.ErrorCode.INVALID_CAFE_NAME;
import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE_DETAIL;
import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_CAFE_IMAGE;
import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.hash;
import static java.util.Objects.isNull;

@Entity
public class Cafe {

    public static final int NAME_MAX_LENGTH = 20;
    public static final int ADDRESS_MAX_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Embedded
    private Images images;

    @Embedded
    private Detail detail;

    private int likeCount;

    protected Cafe() {
    }

    public Cafe(final Long id, final String name, final String address, final Images images, final Detail detail,
                final int likeCount) {
        validate(name, address, images, detail, likeCount);
        this.id = id;
        this.name = name;
        this.address = address;
        this.images = images;
        this.detail = detail;
        this.likeCount = likeCount;
    }

    private void validate(final String name, final String address, final Images images, final Detail detail,
                          final int likeCount) {
        if (isBlank(name) || name.length() > NAME_MAX_LENGTH) {
            throw new BadRequestException(INVALID_CAFE_NAME);
        }
        if (isBlank(address) || address.length() > ADDRESS_MAX_LENGTH) {
            throw new BadRequestException(INVALID_CAFE_ADDRESS);
        }
        if (isNull(images)) {
            throw new BadRequestException(NOT_EXISTED_CAFE_IMAGE);
        }
        if (isNull(detail)) {
            throw new BadRequestException(NOT_EXISTED_CAFE_DETAIL);
        }
        if (likeCount < 0) {
            throw new BadRequestException(INVALID_CAFE_LIKE_COUNT);
        }
    }

    public Cafe(String name, String address, Images images, Detail detail) {
        this(null, name, address, images, detail, 0);
    }

    public void update(final Cafe forUpdate) {
        if (!forUpdate.equals(this)) {
            return;
        }

        this.address = forUpdate.address;
        this.detail = forUpdate.detail;
        this.images = forUpdate.images;
        this.name = forUpdate.name;
        this.likeCount = forUpdate.likeCount;
    }

    public void addLikeCount() {
        likeCount += 1;
    }

    public void subtractLikeCount() {
        if (likeCount == 0) {
            return;
        }

        likeCount -= 1;
    }

    public String getRepresentativeImage() {
        return images.getRepresentativeImage();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Images getImages() {
        return images;
    }

    public Detail getDetail() {
        return detail;
    }

    public int getLikeCount() {
        return likeCount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Cafe cafe = (Cafe) o;
        return Objects.equals(id, cafe.id);
    }

    @Override
    public int hashCode() {
        return hash(id);
    }
}
