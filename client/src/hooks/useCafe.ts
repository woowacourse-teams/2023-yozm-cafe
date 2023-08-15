import client from '../client';
import type { Cafe } from '../types';
import useSuspenseQuery from './useSuspenseQuery';

const useCafe = (cafeId: Cafe['id']) => {
  return useSuspenseQuery({
    queryKey: ['cafe', cafeId],
    queryFn: () => client.getCafe(cafeId),
  });
};

export default useCafe;
