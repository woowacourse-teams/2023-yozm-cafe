package com.project.yozmcafe.domain.menu;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.exception.BadRequestException;
import jakarta.persistence.*;

import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_MENU_BOARD_IMAGE;
import static io.micrometer.common.util.StringUtils.isBlank;

@Entity
public class MenuBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cafe cafe;

    @Column(nullable = false)
    private int priority;

    @Column(nullable = false)
    private String imageUrl;

    public MenuBoard() {
    }

    public MenuBoard(final Cafe cafe, final int priority, final String imageUrl) {
        this(null, cafe, priority, imageUrl);
    }

    public MenuBoard(final Long id, final Cafe cafe, final int priority, final String imageUrl) {
        validate(imageUrl);
        this.id = id;
        this.priority = priority;
        this.cafe = cafe;
        this.imageUrl = imageUrl;
    }

    private void validate(final String imageUrl) {
        if (isBlank(imageUrl)) {
            throw new BadRequestException(NOT_EXISTED_MENU_BOARD_IMAGE);
        }
    }

    public Long getId() {
        return id;
    }

    public Cafe getCafe() {
        return cafe;
    }

    public int getPriority() {
        return priority;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
