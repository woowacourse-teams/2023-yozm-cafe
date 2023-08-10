import client from '../client';
import useSuspenseQuery from './useSuspenseQuery';

const useCafe = (cafeId: number) => {
  return useSuspenseQuery({
    queryKey: ['cafe', cafeId],
    queryFn: () => client.getCafe(cafeId),
  });
};

export default useCafe;
