import React, { Suspense } from 'react';
import { createBrowserRouter } from 'react-router-dom';
import Root from './pages/Root';
const AuthPage = React.lazy(() => import('./pages/AuthPage'));
const CafePage = React.lazy(() => import('./pages/CafePage'));
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
    errorElement: <NotFoundPage />,
    children: [
      { index: true, element: <HomePage /> },
      { path: 'my-profile', element: <MyProfilePage /> },
      { path: '/cafes/:cafeId', element: <CafePage /> },
      { path: 'rank', element: <RankPage /> },
      { path: 'my-profile/cafes/:cafeId', element: <LikedCafeDetailPage /> },
    ],
  },
  {
    path: '/auth/:provider',
    element: (
      <Suspense fallback={<LoadingPage />}>
        <AuthPage />
      </Suspense>
    ),
  },
  {
    path: '/test',
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
]);

export default router;
