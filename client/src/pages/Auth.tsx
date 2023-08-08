import { useEffect } from 'react';
import { Navigate, json, useParams, useSearchParams } from 'react-router-dom';
import useAuth from '../hooks/useAuth';
import type { AuthProvider } from '../types';

const isAuthProvider = (provider: string | undefined): provider is AuthProvider => {
  return (['kakao', 'google'] satisfies AuthProvider[]).some((it) => it === provider);
};

const Auth = () => {
  const { provider } = useParams();
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code');

  if (!isAuthProvider(provider)) {
    throw json(
      {
        message: '올바른 provider가 아닙니다. provider는 kakao 혹은 google 중 하나여야 합니다.',
      },
      { status: 400 },
    );
  }

  if (!code) {
    throw json(
      {
        message: 'Authorization Code가 필요합니다.',
      },
      { status: 400 },
    );
  }

  const { identity, issueAccessToken } = useAuth();

  useEffect(() => {
    issueAccessToken({ provider, code });
  }, []);

  if (!identity) return <></>;

  return <Navigate to="/" />;
};

export default Auth;
