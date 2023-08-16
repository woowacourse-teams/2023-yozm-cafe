import { useInfiniteQuery } from '@tanstack/react-query';
import client from '../client';

const useRank = () => {
  const queryResult = useInfiniteQuery({
    queryKey: ['rank'],
    queryFn: ({ pageParam = 1 }) =>
      client.getCafeRank(pageParam).then((rankedCafes) => ({ rankedCafes, page: pageParam })),
    getNextPageParam: (lastPage) => (lastPage.rankedCafes.length > 0 ? lastPage.page + 1 : undefined),
  });

  return {
    ...queryResult,
    data: queryResult.data as NonNullable<typeof queryResult.data>,
    rankedCafes: queryResult.data?.pages.flatMap(({ rankedCafes }) => rankedCafes) ?? [],
  };
};

export default useRank;
