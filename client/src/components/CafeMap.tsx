import { useEffect, useRef } from 'react';
import { INITIAL_ZOOM_SIZE } from '../constants';
import { useCurrentPosition } from '../hooks/useCurrentPosition';

const CafeMap = () => {
  const ref = useRef<HTMLDivElement | null>(null);
  const position = useCurrentPosition();

  useEffect(() => {
    if (ref.current) {
      // eslint-disable-next-line no-new
      new window.google.maps.Map(ref.current, {
        center: position,
        zoom: INITIAL_ZOOM_SIZE,
      });
    }
  }, [position]);

  return <div ref={ref} id="map" style={{ minHeight: '100vh' }} />;
};

export default CafeMap;
