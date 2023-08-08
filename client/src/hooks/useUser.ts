import client from '../client';
import useAuth from './useAuth';
import useSuspenseQuery from './useSuspenseQuery';

/**
 * identity로 사용자 정보를 조회하고 응답받은 값을 반환합니다.
 *
 * @example
 * const Example = () => {
 *   const { data: user } = useUser();
 *
 *   return user
 *     ? <div>{user.name}님 안녕하세요!</div>
 *     : <div>로그아웃 상태네요!</div>
 * }
 */
const useUser = () => {
  const { identity } = useAuth();

  return useSuspenseQuery({
    queryKey: ['user', identity],
    queryFn: () => client.getUser(identity?.sub as string),
    enabled: !!identity,
  });
};

export default useUser;
