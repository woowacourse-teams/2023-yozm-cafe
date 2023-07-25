import { Dispatch, PropsWithChildren, createContext, useMemo, useState } from 'react';
import { Identity } from '../types';

type AuthContextValue = {
  identity: Identity | null;
  setIdentity: Dispatch<Identity | null>;
};

const AuthContext = createContext<AuthContextValue | null>(null);

type AuthProviderProps = PropsWithChildren;

export const AuthProvider = (props: AuthProviderProps) => {
  const { children } = props;
  const [identity, setIdentity] = useState<Identity | null>(null);

  const contextValue = useMemo(
    () => ({
      identity,
      setIdentity,
    }),
    [identity],
  );

  return <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>;
};

export default AuthContext;
