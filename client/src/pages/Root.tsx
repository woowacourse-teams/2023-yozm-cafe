import { Outlet } from 'react-router-dom';
import { styled } from 'styled-components';
import Navbar from '../components/Navbar';

const Root = () => {
  return (
    <>
      <Outlet />
      <FixedNavbar>
        <Navbar />
      </FixedNavbar>
    </>
  );
};

const FixedNavbar = styled.div`
  position: fixed;
  z-index: 999;
  bottom: 0;
  left: 50%;
  transform: translate(-50%);

  width: 491px;
`;

export default Root;
