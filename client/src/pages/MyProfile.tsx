import { Navigate } from 'react-router';
import styled from 'styled-components';
import Button from '../components/Button';
import LikedCafeList from '../components/LikedCafeList';
import ProfileInfo from '../components/ProfileInfo';
import useUser from '../hooks/useUser';

const MyProfile = () => {
  const { data: user } = useUser();

  if (!user) {
    return <Navigate to="/login" />;
  }

  return (
    <Container>
      <ProfileInfo userImage={user.imageUrl} userName={user.name} />
      <ButtonContainer>
        <EditButtonContainer>
          <Button fullWidth>프로필 수정하기</Button>
        </EditButtonContainer>
        <LogOutButton>
          <Button variant="outlined" fullWidth>
            로그아웃
          </Button>
        </LogOutButton>
      </ButtonContainer>
      <LikedCafeListContainer>
        <LikedCafeList />
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
  margin: ${({ theme }) => theme.space['3']} 0;
`;

const EditButtonContainer = styled.div`
  flex: 6;
  margin-right: ${({ theme }) => theme.space['2.5']};
`;

const LogOutButton = styled.div`
  flex: 4;
`;

const LikedCafeListContainer = styled.div`
  overflow-y: hidden;
  flex: 1;
`;

export default MyProfile;
