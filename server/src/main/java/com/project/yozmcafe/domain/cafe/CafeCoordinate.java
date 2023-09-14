package com.project.yozmcafe.domain.cafe;

import static jakarta.persistence.GenerationType.IDENTITY;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity(name = "cafe_coordinates")
public class CafeCoordinate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Point coordinate;

    @OneToOne
    private Cafe cafe;

    public CafeCoordinate() {
    }

    public CafeCoordinate(final Long id, final Point coordinate, final Cafe cafe) {
        this.id = id;
        this.coordinate = coordinate;
        this.cafe = cafe;
    }

    public CafeCoordinate(final Point coordinate, final Cafe cafe) {
        this(null, coordinate, cafe);
    }

    public double getLatitude() {
        return coordinate.getX();
    }

    public double getLongitude() {
        return coordinate.getY();
    }

    public Long getId() {
        return id;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public Cafe getCafe() {
        return cafe;
    }
}
