import { createBrowserRouter } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import MyProfile from './pages/MyProfile';
import NotFound from './pages/NotFound';
import Root from './pages/Root';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Root />,
    errorElement: <NotFound />,
    children: [
      { index: true, element: <Home /> },
      { path: 'my-profile', element: <MyProfile /> },
      { path: 'login', element: <Login /> },
    ],
  },
]);

export default router;
