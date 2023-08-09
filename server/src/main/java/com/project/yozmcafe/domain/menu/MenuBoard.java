package com.project.yozmcafe.domain.menu;

import com.project.yozmcafe.domain.cafe.Cafe;
import jakarta.persistence.*;

@Entity
public class MenuBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cafe cafe;

    private String imageUrl;

    public MenuBoard() {
    }

    public MenuBoard(final Long id, final Cafe cafe, final String imageUrl) {
        this.id = id;
        this.cafe = cafe;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public Cafe getCafe() {
        return cafe;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
