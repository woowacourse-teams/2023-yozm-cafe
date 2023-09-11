import { useEffect } from 'react';
import type { Position } from '../types';

type UserMarkerProps = {
  map: google.maps.Map;
  position: Position;
};

const UserMarker = (props: UserMarkerProps) => {
  const { map, position } = props;

  useEffect(() => {
    const newMarker = new google.maps.Marker({
      position: { lat: position.lat, lng: position.lng },
      map,
    });

    return () => {
      newMarker.setMap(null);
    };
  }, [map, position.lat, position.lng]);

  return <></>;
};

export default UserMarker;
