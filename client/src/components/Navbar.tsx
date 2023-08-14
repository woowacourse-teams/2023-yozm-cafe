import { Suspense, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import useUser from '../hooks/useUser';
import Button from './Button';
import LoginModal from './LoginModal';
import Logo from './Logo';

const Navbar = () => {
  const { data: user } = useUser();
  const navigate = useNavigate();
  const [isLoginModalOpen, setIsLoginModalOpen] = useState(false);

  const openLoginModal = () => {
    setIsLoginModalOpen(true);
  };

  const closeLoginModal = () => {
    setIsLoginModalOpen(false);
  };

  const handleLogoClick = () => {
    navigate('/');
  };

  const handleRankClick = () => {
    navigate('/rank');
  };

  const handleProfileClick = () => {
    navigate('/my-profile');
  };

  return (
    <Container>
      <LogoContainer>
        <Logo onClick={handleLogoClick} fontSize="4xl" />
      </LogoContainer>
      <ButtonContainer>
        <RankButtonContainer>
          <Button $fullWidth $fullHeight $variant="secondary" onClick={handleRankClick}>
            랭킹
          </Button>
        </RankButtonContainer>
        <LoginAndProfileButtonContainer>
          {user ? (
            <Button $variant="outlined" $fullWidth $fullHeight onClick={handleProfileClick}>
              프로필
            </Button>
          ) : (
            <Button $fullWidth $fullHeight onClick={openLoginModal} aria-haspopup="dialog">
              로그인
            </Button>
          )}
        </LoginAndProfileButtonContainer>
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
  display: flex;
  flex: 4;
  align-items: center;
`;

const LogoContainer = styled.div`
  display: flex;
  flex: 6;
`;

const RankButtonContainer = styled.div`
  flex: 1;
  margin-right: ${({ theme }) => theme.space[2]};
`;

const LoginAndProfileButtonContainer = styled.div`
  flex: 3;
`;
