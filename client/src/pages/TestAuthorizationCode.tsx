import { Navigate, useParams } from 'react-router-dom';

const TestAuthorizationCode = () => {
  const { provider } = useParams();

  return <Navigate to={`/auth/${provider}?code=test-authorization-code`} />;
};

export default TestAuthorizationCode;
