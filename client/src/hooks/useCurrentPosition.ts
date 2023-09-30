import { useEffect, useState } from 'react';

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
        alert('위치 권한을 허용해주세요.');
      },
      {
        enableHighAccuracy: true,
      },
    );
  }, []);

  return position;
};

export default useCurrentPosition;
