import { useEffect, useRef } from 'react';
import { INITIAL_CENTER, INITIAL_ZOOM_SIZE } from '../constants';

const CafeMap = () => {
  const ref = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (ref.current) {
      // eslint-disable-next-line no-new
      new window.google.maps.Map(ref.current, {
        center: INITIAL_CENTER,
        zoom: INITIAL_ZOOM_SIZE,
      });
    }
  }, []);

  return <div ref={ref} id="map" style={{ minHeight: '100vh' }} />;
};

export default CafeMap;
