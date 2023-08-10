import client from '../client';
import type { Cafe } from '../types';
import useSuspenseQuery from './useSuspenseQuery';

const useCafeMenus = (cafeId: Cafe['id']) => {
  return useSuspenseQuery({
    queryKey: ['cafes', cafeId, 'menus'],
    queryFn: () => client.getCafeMenu(cafeId),
  });
};

export default useCafeMenus;
