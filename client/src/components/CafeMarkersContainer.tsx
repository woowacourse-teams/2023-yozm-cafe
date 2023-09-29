import useCafesNearLocation from '../hooks/useCafesNearLocation';
import CafeMarkers from './CafeMarkers';

type CafeMarkersContainerProps = {
  map: google.maps.Map;
};

const CafeMarkersContainer = (props: CafeMarkersContainerProps) => {
  const { map } = props;
  const { ...queryInto } = useCafesNearLocation(map);
  const cafes = queryInto.data;

  if (!cafes || !queryInto.isSuccess) return <></>;

  return (
    <>
      {cafes.map((cafe) => (
        <CafeMarkers key={cafe.id} map={map} cafe={cafe} />
      ))}
    </>
  );
};

export default CafeMarkersContainer;
