import type { Dispatch } from 'react';
import { useEffect, useState } from 'react';

/**
 * useState와 localStorage가 결합된 훅입니다. 영구 저장되는 상태를 이용할 수 있습니다.
 * 상태 값은 `JSON.stringify` 및 `JSON.parse` 가능한 값으로 사용하여야 합니다.
 *
 * @example
 * const Example = () => {
 *   const [accessToken, setAccessToken] = usePersistedState(null);
 *
 *   const onLogin = () => {
 *     login().then((response) => setAccessToken(response.data.accessToken));
 *   }
 *
 *   return (
 *     // ...
 *   )
 * }
 */
const usePersistedState = <T extends string | number | object | null | undefined>(
  key: string,
  initialValue: T,
): [T, Dispatch<T>] => {
  const serializedItem = localStorage.getItem(key);
  const [state, setState] = useState<T>(serializedItem ? JSON.parse(serializedItem) : initialValue);

  useEffect(() => {
    localStorage.setItem(key, JSON.stringify(state));
  }, [state]);

  return [state, setState];
};

export default usePersistedState;
