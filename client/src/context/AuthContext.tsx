import type { Dispatch, PropsWithChildren } from 'react';
import { createContext, useMemo } from 'react';
import client from '../client';
import usePersistedState from '../hooks/usePersistedState';
import type { Identity } from '../types';

type AuthContextValue = {
  accessToken: string | null;
  setAccessToken: Dispatch<string | null>;
  identity: Identity | null;
};

const AuthContext = createContext<AuthContextValue | null>(null);

type AuthProviderProps = PropsWithChildren;

export const AuthProvider = (props: AuthProviderProps) => {
  const { children } = props;
  const [accessToken, setAccessToken] = usePersistedState<string | null>('accessToken', null);

  const identity = useMemo(() => {
    client.setAccessToken(accessToken);
    client.onAccessTokenRefresh(setAccessToken);

    if (!accessToken) return null;

    const [header, payload, verifySignature] = accessToken.split('.');
    return JSON.parse(window.atob(payload)) as Identity;
  }, [accessToken]);

  const contextValue = useMemo(
    () => ({
      accessToken,
      setAccessToken,
      identity,
    }),
    [accessToken, setAccessToken, identity],
  );

  return <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>;
};

export default AuthContext;
