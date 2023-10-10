package com.project.yozmcafe.domain.cafe.coordinate;

import java.util.List;

import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.yozmcafe.domain.cafe.coordinate.dto.CafePinDto;

public interface CafeCoordinateRepository extends JpaRepository<CafeCoordinate, Long> {

    @Query("""
            SELECT
                co.cafe.id AS id,
                co.cafe.name AS name,
                co.cafe.address AS address,
                ST_X(co.coordinate) AS latitude,
                ST_Y(co.coordinate) AS longitude
            FROM CafeCoordinate co
            WHERE ST_CONTAINS(:area, co.coordinate)
            """)
    List<CafePinDto> findCafePinsFromCoordinate(@Param("area") final Polygon area);
}
