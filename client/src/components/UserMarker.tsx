import { useEffect } from 'react';

type UserMarkerProps = {
  map: google.maps.Map;
  currentPosition: google.maps.LatLngLiteral;
};

const UserMarker = (props: UserMarkerProps) => {
  const { map, currentPosition } = props;
  const { lat, lng } = currentPosition;

  useEffect(() => {
    const newMarker = new google.maps.Marker({
      position: { lat, lng },
      map,
      icon: '/images/current-position-icon.png',
    });

    return () => {
      newMarker.setMap(null);
    };
  }, [lat, lng, map]);

  return <></>;
};

export default UserMarker;
