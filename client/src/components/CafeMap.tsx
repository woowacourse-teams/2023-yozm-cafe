import { useQueryClient } from '@tanstack/react-query';
import { useEffect, useRef, useState } from 'react';
import { styled } from 'styled-components';
import { INITIAL_CENTER, INITIAL_ZOOM_SIZE } from '../constants';
import { useCurrentPosition } from '../hooks/useCurrentPosition';
import CafeMarkersContainer from './CafeMarkersContainer';
import UserMarker from './UserMarker';

type CafeMapProps = {
  map: google.maps.Map;
};

const CafeMapListener = (props: CafeMapProps) => {
  const { map } = props;
  const queryClient = useQueryClient();

  useEffect(() => {
    map.addListener('dragend', () => {
      queryClient.invalidateQueries({ queryKey: ['cafesNearLocation'] });
    });

    map.addListener('zoom_changed', () => {
      queryClient.invalidateQueries({ queryKey: ['cafesNearLocation'] });
    });

    const initMarkersEvent = map.addListener('bounds_changed', () => {
      queryClient.invalidateQueries({ queryKey: ['cafesNearLocation'] });
      google.maps.event.removeListener(initMarkersEvent);
    });
  }, [map, queryClient]);

  return <></>;
};

const CafeMap = () => {
  const ref = useRef<HTMLDivElement | null>(null);
  const [googleMap, setGoogleMap] = useState<google.maps.Map>();
  const [position, setPosition] = useState(INITIAL_CENTER);
  const currentPosition = useCurrentPosition();

  const isClientReady = currentPosition !== undefined && googleMap !== undefined;

  const moveToCurrentUserLocation = () => {
    if (googleMap && currentPosition) {
      const { lat, lng } = currentPosition;
      const bounds = new google.maps.LatLngBounds(
        new google.maps.LatLng(37.5353, 127.0299),
        new google.maps.LatLng(37.5543, 127.0637),
      );

      // 원하는 위치가 범위 내에 있는지 확인합니다.
      if (bounds.contains(new google.maps.LatLng(lat, lng))) {
        googleMap.panTo({ lat, lng });
        setPosition({ lat, lng });
      } else {
        // 경고 메시지를 표시합니다.
        alert('서비스는 현재 성수 지역에서만 이용 가능합니다.');
        googleMap.panTo(INITIAL_CENTER);
        setPosition(INITIAL_CENTER);
      }
    }
  };

  const moveToSungsuCafeRoadLocation = () => {
    if (googleMap) {
      googleMap.panTo(INITIAL_CENTER);
      setPosition(INITIAL_CENTER);
    }
  };

  useEffect(() => {
    if (ref.current) {
      const initialMap = new window.google.maps.Map(ref.current, {
        center: position,
        zoom: INITIAL_ZOOM_SIZE,
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

      setGoogleMap(initialMap);
    }
  }, [position]);

  return (
    <>
      <div ref={ref} id="map" style={{ minHeight: '100vh' }} />
      <MapLocationButtonContainer>
        <MapLocationButton onClick={moveToCurrentUserLocation}>내 위치로 이동</MapLocationButton>
        <MapLocationButton onClick={moveToSungsuCafeRoadLocation}>성수동 카페거리로 이동</MapLocationButton>
      </MapLocationButtonContainer>
      {isClientReady && (
        <>
          <CafeMarkersContainer map={googleMap} />
          <UserMarker map={googleMap} currentPosition={currentPosition} />
          <CafeMapListener map={googleMap} />
        </>
      )}
    </>
  );
};

export default CafeMap;

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
  cursor: pointer;

  padding: 10px;

  color: ${({ theme }) => theme.color.black};

  background-color: ${({ theme }) => theme.color.white};
  border: none;
  border-radius: 10px;
  box-shadow: ${({ theme }) => theme.shadow.map};
`;
