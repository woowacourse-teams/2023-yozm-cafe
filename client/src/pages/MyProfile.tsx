import styled from 'styled-components';
import Button from '../components/Button';
import LikedCafeList from '../components/LikedCafeList';
import Logo from '../components/Logo';
import ProfileInfo from '../components/ProfileInfo';

const MyProfile = () => {
  return (
    <Container>
      <Logo fontSize="2xl" />
      <ProfileInfo userImage="/images/profile-example.png" userName="김고니" />
      <ButtonContainer>
        <EditButtonContainer>
          <Button fullWidth={true}>프로필 수정하기</Button>
        </EditButtonContainer>
        <LogOutButton>
          <Button variant="outlined" fullWidth={true}>
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
  width: 60%;
  margin-right: ${({ theme }) => theme.space['2.5']};
`;

const LogOutButton = styled.div`
  width: 40%;
`;

const LikedCafeListContainer = styled.div`
  overflow-y: hidden;
  flex: 1;
`;

export default MyProfile;
