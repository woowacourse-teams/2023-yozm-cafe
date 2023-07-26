package com.project.yozmcafe.domain.cafe;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Cafe {
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

    public Cafe(final Long id, final String name, final String address, final Images images, final Detail detail, final int likeCount) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.images = images;
        this.detail = detail;
        this.likeCount = likeCount;
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
        return Objects.hash(id);
    }
}
