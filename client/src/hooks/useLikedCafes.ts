import { useInfiniteQuery } from '@tanstack/react-query';
import client from '../client';
import useUser from './useUser';

/**
 * `/members/{userId}/liked-cafes` 엔드포인트에서 받아오는 데이터(상태)를 사용할 수 있는 커스텀 훅입니다.
 * react-query의 `useInfiniteQuery` 를 사용하여 구현되었습니다.
 *
 * @example
 * ```
 * const Example = () => {
 *   const { likedCafes, fetchNextPage } = useLikedCafes('사용자의 ID');
 *
 *   useEffect(() => {
 *     fetchNextPage(); // 마운트 되었을 때 다음 페이지를 fetch
 *   }, []);
 *
 *   return <>{likedCafes.map((cafe) => <CafeExample key={cafe.id} cafe={cafe} />)}</>
 * }
 * ```
 */
const useLikedCafes = () => {
  const { data: user } = useUser();
  const queryResult = useInfiniteQuery({
    queryKey: ['likedCafes', user?.id],
    queryFn: ({ pageParam = 1 }) =>
      client.getLikedCafeList(user?.id as string, pageParam).then((likedCafes) => ({ likedCafes, page: pageParam })),
    getNextPageParam: (lastPage) => (lastPage.likedCafes.length > 0 ? lastPage.page + 1 : undefined),
    enabled: !!user,
  });

  return {
    ...queryResult,
    data: queryResult.data as NonNullable<typeof queryResult.data>,
    likedCafes: queryResult.data?.pages.flatMap(({ likedCafes }) => likedCafes) ?? [],
  };
};

export default useLikedCafes;
