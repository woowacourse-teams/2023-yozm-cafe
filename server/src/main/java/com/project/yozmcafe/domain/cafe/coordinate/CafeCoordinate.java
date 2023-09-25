package com.project.yozmcafe.domain.cafe.coordinate;

import static jakarta.persistence.GenerationType.IDENTITY;

import org.locationtech.jts.geom.Point;

import com.project.yozmcafe.domain.cafe.Cafe;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class CafeCoordinate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Point coordinate;

    @OneToOne(fetch = FetchType.LAZY)
    private Cafe cafe;

    protected CafeCoordinate() {
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
        return coordinate.getY();
    }

    public double getLongitude() {
        return coordinate.getX();
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
