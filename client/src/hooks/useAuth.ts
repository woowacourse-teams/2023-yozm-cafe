import { useQuery } from '@tanstack/react-query';
import client from '../client';
import { AuthProvider } from '../types';

type AuthOptions = {
  provider: AuthProvider;
  code: string;
};

const useAuth = ({ provider, code }: AuthOptions) => {
  const { data } = useQuery({
    queryKey: ['auth'],
    queryFn: () => client.requestAccessToken(provider, code),
    cacheTime: 0,
    refetchInterval: false,
    refetchOnWindowFocus: false,
  });

  const accessToken = data?.accessToken as string;
  client.setAccessToken(accessToken);

  return { accessToken };
};

export default useAuth;
