import { Suspense, useEffect } from 'react';
import { Outlet } from 'react-router-dom';
import { styled } from 'styled-components';
import Navbar from '../components/Navbar';
import useAuth from '../hooks/useAuth';
import useSilentLink from '../hooks/useSilentLink';
import { setGAConfig } from '../utils/GoogleAnalytics';

const Root = () => {
  const { identity } = useAuth();
  useSilentLink();

  useEffect(() => {
    if (identity) {
      setGAConfig({
        user_id: identity.sub,
      });
    }
  }, [identity]);

  return (
    <>
      <Navbar />
      <PageContent>
        <Suspense>
          <Outlet />
        </Suspense>
      </PageContent>
    </>
  );
};

const PageContent = styled.main`
  overflow-y: auto;
  flex: 1;
`;

export default Root;
