import client from '../client';
import useSuspenseQuery from './useSuspenseQuery';

const useAuthUrls = () => {
  return useSuspenseQuery({
    queryKey: ['auth', 'urls'],
    queryFn: () => client.getAuthUrls(),
  });
};

export default useAuthUrls;
