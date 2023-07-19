import { Outlet } from 'react-router-dom';
import { styled } from 'styled-components';
import Navbar from '../components/Navbar';

const Root = () => {
  return (
    <>
      <PageContent>
        <Outlet />
      </PageContent>
      <Navbar />
    </>
  );
};

const PageContent = styled.main`
  overflow-y: auto;
  flex: 1;
`;

export default Root;
