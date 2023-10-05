import { useEffect, useState } from 'react';
import { SEONGSU_CAFE_STREET_LOCATION } from '../constants';

const useCurrentPosition = () => {
  const [position, setPosition] = useState<google.maps.LatLngLiteral | null>(null);

  useEffect(() => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        setPosition({
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        });
      },
      () => {
        setPosition(SEONGSU_CAFE_STREET_LOCATION);
      },
      {
        enableHighAccuracy: true,
      },
    );
  }, []);

  return position;
};

export default useCurrentPosition;
