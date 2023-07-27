import { useQuery } from '@tanstack/react-query';
import client from '../client';

const useAuthUrls = () => {
  const queryResult = useQuery({
    queryKey: ['auth', 'urls'],
    queryFn: () => client.getAuthUrls(),
  });

  return { ...queryResult, data: queryResult.data as NonNullable<typeof queryResult.data> };
};

export default useAuthUrls;
