import client from '../client';
import useSuspenseQuery from './useSuspenseQuery';

const useCafesNearLocation = (map: google.maps.Map) => {
  return useSuspenseQuery({
    queryKey: ['cafesNearLocation'],
    queryFn: () => client.getCafesNearLocation(map),
  });
};

export default useCafesNearLocation;
