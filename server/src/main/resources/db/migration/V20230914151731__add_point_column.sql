CREATE TABLE cafe_coordinate
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    coordinate POINT  NOT NULL SRID 4326,
    cafe_id    BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cafe_id) REFERENCES cafe (id)
);

CREATE SPATIAL INDEX idx_coordinates ON cafe_coordinates (coordinate);
