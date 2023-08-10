package com.project.yozmcafe.domain.menu;

import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.exception.BadRequestException;
import jakarta.persistence.*;

import static com.project.yozmcafe.exception.ErrorCode.INVALID_MENU_NAME;
import static com.project.yozmcafe.exception.ErrorCode.NOT_EXISTED_MENU_PRICE;
import static io.micrometer.common.util.StringUtils.isBlank;

@Entity
@Table(
    name = "Menu",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UK_cafe_priority",
            columnNames = {
                "cafe_id",
                "priority"
            }
        )
    }
)
public class Menu {

    public static final int MAX_NAME_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cafe cafe;

    @Column(nullable = false)
    private Long priority;

    @Column(nullable = false)
    private String name;

    private String imageUrl;

    private String description;

    @Column(nullable = false)
    private String price;

    private boolean isRecommended;

    protected Menu() {
    }

    public Menu(final Cafe cafe, final Long priority, final String name, final String imageUrl, final String description, final String price, final boolean isRecommended) {
        this(null, cafe, priority, name, imageUrl, description, price, isRecommended);
    }

    public Menu(final Long id, final Cafe cafe, final Long priority, final String name, final String imageUrl, final String description,
                final String price, final boolean isRecommended) {
        validate(name, price);
        this.id = id;
        this.cafe = cafe;
        this.priority = priority;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
        this.isRecommended = isRecommended;
    }

    private void validate(final String name, final String price) {
        if (isBlank(name) || name.length() > MAX_NAME_LENGTH) {
            throw new BadRequestException(INVALID_MENU_NAME);
        }
        if (isBlank(price)) {
            throw new BadRequestException(NOT_EXISTED_MENU_PRICE);
        }
    }

    public Long getId() {
        return id;
    }

    public Cafe getCafe() {
        return cafe;
    }

    public Long getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public boolean isRecommended() {
        return isRecommended;
    }
}
