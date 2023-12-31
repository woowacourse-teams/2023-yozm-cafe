import client from '../client';
import useAuth from './useAuth';
import useSuspenseQuery from './useSuspenseQuery';
import useUser from './useUser';

const useLikedCafesDetail = () => {
  const { data: user } = useUser();
  const { identity } = useAuth();

  const queryResult = useSuspenseQuery({
    queryKey: ['likedCafesDetail', identity],
    queryFn: () => client.getLikedCafeDetail(user?.id as string),
    enabled: !!user,
  });

  return {
    ...queryResult,
    likedCafesDetail: queryResult.data,
  };
};

export default useLikedCafesDetail;
