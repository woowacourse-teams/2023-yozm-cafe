import { Outlet } from 'react-router-dom';
import Navbar from '../components/Navbar';

const Root = () => {
  return (
    <>
      <Outlet />
      <Navbar />
    </>
  );
};

export default Root;
