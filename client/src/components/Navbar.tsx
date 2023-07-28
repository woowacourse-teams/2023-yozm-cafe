import { Suspense, useState } from 'react';
import { Link } from 'react-router-dom';
import { styled } from 'styled-components';
import useUser from '../hooks/useUser';
import Button from './Button';
import LoginModal from './LoginModal';
import Logo from './Logo';

const Navbar = () => {
  const { data: user } = useUser();
  const [isLoginModalOpen, setIsLoginModalOpen] = useState(false);

  const openLoginModal = () => {
    setIsLoginModalOpen(true);
  };

  const closeLoginModal = () => {
    setIsLoginModalOpen(false);
  };

  return (
    <Container>
      <LogoContainer to="/">
        <Logo fontSize="4xl" />
      </LogoContainer>
      <ButtonContainer>
        {user ? (
          <ProfileImage src={user.imageUrl} alt="Profile" />
        ) : (
          <Button fullWidth={true} fullHeight={true} onClick={openLoginModal}>
            로그인
          </Button>
        )}
      </ButtonContainer>
      <Suspense>{isLoginModalOpen && <LoginModal onClose={closeLoginModal} />}</Suspense>
    </Container>
  );
};

export default Navbar;

const Container = styled.nav`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 66px;
  padding: 0 ${({ theme }) => theme.space[4]};
`;

const ButtonContainer = styled.div`
  flex: 2;
  height: 55%;
`;

const LogoContainer = styled(Link)`
  text-decoration: none;
`;

const ProfileImage = styled.img`
  height: 100%;
  border-radius: 100%;
`;
