import React, { Suspense } from 'react';
import { createBrowserRouter } from 'react-router-dom';
import ErrorPage from './pages/ErrorPage';
import Root from './pages/Root';

const SearchPage = React.lazy(() => import('./pages/SearchPage'));
const AuthPage = React.lazy(() => import('./pages/AuthPage'));
const CafePage = React.lazy(() => import('./pages/CafePage'));
const CafeMapPage = React.lazy(() => import('./pages/CafeMapPage'));
const HomePage = React.lazy(() => import('./pages/HomePage'));
const LikedCafeDetailPage = React.lazy(() => import('./pages/LikedCafeDetailPage'));
const LoadingPage = React.lazy(() => import('./pages/LoadingPage'));
const MyProfilePage = React.lazy(() => import('./pages/MyProfilePage'));
const NotFoundPage = React.lazy(() => import('./pages/NotFoundPage'));
const RankPage = React.lazy(() => import('./pages/RankPage'));
const TestAuthorizationCodePage = React.lazy(() => import('./pages/TestAuthorizationCodePage'));

const router = createBrowserRouter([
  {
    path: '/',
    element: <Root />,
    children: [
      {
        path: '*',
        errorElement: <ErrorPage />,
        children: [
          { index: true, element: <HomePage /> },
          { path: 'cafes/:cafeId', element: <CafePage /> },
          { path: 'rank', element: <RankPage /> },
          { path: 'my-profile', element: <MyProfilePage /> },
          { path: 'my-profile/cafes/:cafeId', element: <LikedCafeDetailPage /> },
          { path: 'search', element: <SearchPage /> },
          { path: 'map', element: <CafeMapPage /> },
          {
            path: 'auth/:provider',
            element: (
              <Suspense fallback={<LoadingPage />}>
                <AuthPage />
              </Suspense>
            ),
          },
          {
            path: 'test',
            children: [
              {
                path: 'auth/:provider',
                element: (
                  <Suspense>
                    <TestAuthorizationCodePage />
                  </Suspense>
                ),
              },
            ],
          },
          {
            path: '*',
            element: <NotFoundPage />,
          },
        ],
      },
    ],
  },
]);

export default router;
