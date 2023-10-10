import client from '../client';
import type { SearchedCafe } from '../types';
import useSuspenseQuery from './useSuspenseQuery';

type SearchCafesQuery = {
  searchName: string;
  searchMenu?: string | undefined;
  searchAddress?: string | undefined;
};

const useSearchCafes = (query: SearchCafesQuery) => {
  const { searchName, searchMenu, searchAddress } = query;

  return useSuspenseQuery<SearchedCafe[]>({
    queryKey: ['searchCafes', query],
    retry: false,
    queryFn: () => client.searchCafes({ name: searchName, menu: searchMenu, address: searchAddress }),
  });
};

export default useSearchCafes;
