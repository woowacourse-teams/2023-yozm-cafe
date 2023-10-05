import { Navigate, useParams } from 'react-router-dom';

const TestAuthorizationCodePage = () => {
  const { provider } = useParams();

  return <Navigate to={`/auth/${provider}?code=test-authorization-code`} />;
};

export default TestAuthorizationCodePage;
