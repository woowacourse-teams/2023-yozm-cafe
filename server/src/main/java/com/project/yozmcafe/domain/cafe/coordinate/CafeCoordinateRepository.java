package com.project.yozmcafe.domain.cafe.coordinate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.yozmcafe.domain.cafe.coordinate.dto.CafePinDto;

public interface CafeCoordinateRepository extends JpaRepository<CafeCoordinate, Long> {

    @Query(nativeQuery = true,
            value = """
                      SELECT c.id, c.name, c.address, ST_X(co.coordinate) AS latitude, ST_Y(co.coordinate) AS longitude
                      FROM cafe_coordinate co
                      JOIN cafe AS c
                      ON co.cafe_id = c.id
                      WHERE ST_CONTAINS(ST_Buffer(ST_GeomFromText(:point, 4326), :radius), co.coordinate);
                    """)
    List<CafePinDto> findCafePinsFromCoordinate(@Param("point") final String point,
                                                @Param("radius") final double radius);
}
