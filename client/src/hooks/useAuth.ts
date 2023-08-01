import { useMutation } from '@tanstack/react-query';
import { useCallback, useContext } from 'react';
import client from '../client';
import AuthContext from '../context/AuthContext';
import { AuthProvider } from '../types';

type AuthParams = {
  provider: AuthProvider;
  code: string;
};

/**
 * 인증 정보(JWT token payload)와 인증 동작을 수행하는 함수를 반환합니다.
 *
 * {@link client.issueAccessToken} 함수로 부터 받은
 * Access Token(JWT)을 파싱하고 payload를 앱에서 identity로 사용한다.
 *
 * @example
 * const Example = ({ provider, code }) => {
 *   const { identity, issueAccessToken } = useAuth();
 *
 *   useEffect(() => {
 *     // code를 accessToken으로 교환한다.
 *     issueAccessToken({ provider, code });
 *   }, []);
 *
 *   return identity
 *     ? <div>당신의 계정 ID: {identity.sub}</div>
 *     : <div>로그인 중 ...</div>;
 * }
 */
const useAuth = () => {
  const authContext = useContext(AuthContext);
  if (!authContext) {
    throw new Error('AuthProvider를 사용해주셔야 합니다.');
  }
  const { accessToken, setAccessToken, identity } = authContext;

  const { mutate: issueAccessToken } = useMutation({
    mutationFn: ({ provider, code }: AuthParams) => client.issueAccessToken(provider, code),
    onSettled: (accessToken) => {
      setAccessToken(accessToken ?? null);
    },
  });

  const { mutate: clearRefreshToken } = useMutation({
    mutationFn: () => client.clearRefreshToken(),
  });

  const clearAuthorization = useCallback(() => {
    setAccessToken(null);
    return clearRefreshToken();
  }, [clearRefreshToken]);

  return {
    accessToken,
    issueAccessToken,
    clearAuthorization,
    identity,
  };
};

export default useAuth;
