import { Suspense } from 'react';
import { createBrowserRouter } from 'react-router-dom';
import Auth from './pages/Auth';
import Cafe from './pages/Cafe';
import Home from './pages/Home';
import Loading from './pages/Loading';
import MyProfile from './pages/MyProfile';
import NotFound from './pages/NotFound';
import Root from './pages/Root';
import TestAuthorizationCode from './pages/TestAuthorizationCode';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Root />,
    errorElement: <NotFound />,
    children: [
      { index: true, element: <Home /> },
      { path: 'my-profile', element: <MyProfile /> },
      { path: '/cafe/:cafeId', element: <Cafe /> },
    ],
  },
  {
    path: '/auth/:provider',
    element: (
      <Suspense fallback={<Loading />}>
        <Auth />
      </Suspense>
    ),
  },
  {
    path: '/test',
    children: [{ path: 'auth/:provider', element: <TestAuthorizationCode /> }],
  },
]);

export default router;
