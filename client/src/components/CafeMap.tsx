import React, { Suspense, useEffect, useRef, useState } from 'react';
import { styled } from 'styled-components';
import { SEONGSU_CAFE_STREET_LOCATION, SEONGSU_MAP_INITIAL_ZOOM_SIZE } from '../constants';
import useCafesNearLocation from '../hooks/useCafesNearLocation';
import useCurrentPosition from '../hooks/useCurrentPosition';
import CafeMarkersContainer from './CafeMarkersContainer';
import UserMarker from './UserMarker';

const CafeMap = () => {
  const ref = useRef<HTMLDivElement>(null);
  const [googleMap, setGoogleMap] = useState<google.maps.Map | null>(null);

  useEffect(() => {
    if (!ref.current) return;

    const googleMap = new window.google.maps.Map(ref.current, {
      center: SEONGSU_CAFE_STREET_LOCATION,
      zoom: SEONGSU_MAP_INITIAL_ZOOM_SIZE,
      disableDefaultUI: true,
      clickableIcons: false,
      mapId: '32c9cce63f7772a8',
      maxZoom: 20,
      minZoom: 14,
      restriction: {
        latLngBounds: {
          north: 37.5543,
          south: 37.5353,
          east: 127.0637,
          west: 127.0299,
        },
      },
    });

    setGoogleMap(googleMap);
  }, []);

  return (
    <>
      <div ref={ref} id="map" style={{ minHeight: '100vh' }} />
      <Suspense>{googleMap && <CafeMapContent map={googleMap} />}</Suspense>
    </>
  );
};

type CafeMapContentProps = {
  map: google.maps.Map;
};

const CafeMapContent = (props: CafeMapContentProps) => {
  const { map } = props;

  const currentPosition = useCurrentPosition();
  const { refetch } = useCafesNearLocation(map);

  const setPosition = (position: { lat: number; lng: number }) => {
    map.setCenter(position);
  };

  const moveToCurrentUserLocation = () => {
    if (!currentPosition) return;

    const { lat, lng } = currentPosition;
    const bounds = new google.maps.LatLngBounds(
      new google.maps.LatLng(37.5353, 127.0299),
      new google.maps.LatLng(37.5543, 127.0637),
    );

    // 원하는 위치가 범위 내에 있는지 확인합니다.
    if (bounds.contains(new google.maps.LatLng(lat, lng))) {
      map.panTo({ lat, lng });
      setPosition({ lat, lng });
    } else {
      // 경고 메시지를 표시합니다.
      alert('서비스는 성수 지역에서만 이용 가능합니다.');
      map.panTo(SEONGSU_CAFE_STREET_LOCATION);
      setPosition(SEONGSU_CAFE_STREET_LOCATION);
    }
  };

  const moveToSungsuCafeRoadLocation = () => {
    map.panTo(SEONGSU_CAFE_STREET_LOCATION);
    setPosition(SEONGSU_CAFE_STREET_LOCATION);
  };

  useEffect(() => {
    const listener = map.addListener('idle', () => {
      refetch();
    });

    return () => google.maps.event.removeListener(listener);
  }, [map]);

  return (
    <>
      <MapLocationButtonContainer>
        <MapLocationButton onClick={moveToCurrentUserLocation}>내 위치로 이동</MapLocationButton>
        <MapLocationButton onClick={moveToSungsuCafeRoadLocation}>성수동 카페거리로 이동</MapLocationButton>
      </MapLocationButtonContainer>

      <CafeMarkersContainer map={map} />
      {currentPosition && <UserMarker map={map} currentPosition={currentPosition} />}
    </>
  );
};

export default React.memo(CafeMap);

const MapLocationButtonContainer = styled.div`
  position: absolute;
  z-index: 999;
  top: 65px;

  display: flex;
  gap: 10px;

  width: 100%;
  padding: 10px;
`;

const MapLocationButton = styled.button`
  padding: 10px;

  color: ${({ theme }) => theme.color.black};

  background-color: ${({ theme }) => theme.color.white};
  border: none;
  border-radius: 10px;
  box-shadow: ${({ theme }) => theme.shadow.map};
`;
