import { Suspense } from 'react';
import { Outlet } from 'react-router-dom';
import { styled } from 'styled-components';
import Navbar from '../components/Navbar';
import useSilentLink from '../hooks/useSilentLink';

const Root = () => {
  useSilentLink();

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
