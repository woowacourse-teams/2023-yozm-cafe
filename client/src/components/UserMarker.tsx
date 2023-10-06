import { useEffect, useRef } from 'react';

type UserMarkerProps = {
  map: google.maps.Map;
  position: google.maps.LatLngLiteral;
};

const UserMarker = (props: UserMarkerProps) => {
  const { map, position } = props;
  const markerRef = useRef<google.maps.Marker | null>(null);

  useEffect(() => {
    const marker = new google.maps.Marker({
      position,
      map,
      icon: '/assets/current-position-icon.png',
    });
    markerRef.current = marker;

    return () => {
      marker.setMap(null);
      markerRef.current = null;
    };
  }, [map]);

  useEffect(() => {
    markerRef.current?.setPosition(position);
  }, [markerRef.current, position.lat, position.lng]);

  return <></>;
};

export default UserMarker;
