import { Navigate, useNavigate } from 'react-router';
import styled from 'styled-components';
import Button from '../components/Button';
import LikedCafeList from '../components/LikedCafeList';
import ProfileInfo from '../components/ProfileInfo';
import useAuth from '../hooks/useAuth';
import useLikedCafes from '../hooks/useLikedCafes';
import useUser from '../hooks/useUser';

const MyProfile = () => {
  const navigate = useNavigate();
  const { data: user } = useUser();
  const { clearAuthorization } = useAuth();
  const { likedCafes, fetchNextPage, isFetchingNextPage } = useLikedCafes();

  if (!user) {
    return <Navigate to="/" />;
  }

  const handleLogout = async () => {
    await clearAuthorization();
    navigate('/');
  };

  const handleScroll = (event: React.UIEvent<HTMLDivElement>) => {
    if (isFetchingNextPage) {
      return;
    }
    const { scrollHeight, scrollTop, clientHeight } = event.currentTarget;
    if (scrollHeight - scrollTop === clientHeight) {
      fetchNextPage();
    }
  };

  return (
    <Container>
      <ProfileInfo userImage={user.imageUrl} userName={user.name} />
      <ButtonContainer>
        <Button $variant="default" $fullWidth onClick={handleLogout}>
          로그아웃
        </Button>
      </ButtonContainer>
      <LikedCafeListContainer onScroll={handleScroll}>
        {/* LikedCafeList 컴포넌트에 likedCafes 데이터 전달 */}
        <LikedCafeList likedCafes={likedCafes} />
      </LikedCafeListContainer>
    </Container>
  );
};

const Container = styled.section`
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: ${({ theme }) => theme.space['7']} ${({ theme }) => theme.space['4']} 0 ${({ theme }) => theme.space['4']};
`;

const ButtonContainer = styled.article`
  display: flex;
  justify-content: center;
  margin: ${({ theme }) => theme.space['3']} 0;

  & > * {
    width: 120px;
  }
`;

const LikedCafeListContainer = styled.div`
  overflow-y: hidden;
  flex: 1;
`;

export default MyProfile;
