import client from '../client';
import { getDisplayPosition } from '../utils/mapUtils';
import useSuspenseQuery from './useSuspenseQuery';

const useCafesNearLocation = (map: google.maps.Map) => {
  const { longitude, latitude, longitudeDelta, latitudeDelta } = getDisplayPosition(map);

  return useSuspenseQuery({
    queryKey: ['cafesNearLocation'],
    queryFn: () => client.getCafesNearLocation(longitude, latitude, longitudeDelta, latitudeDelta),
  });
};

export default useCafesNearLocation;
