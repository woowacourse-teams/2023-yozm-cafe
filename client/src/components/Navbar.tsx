import { Suspense, useState } from 'react';
import { Link } from 'react-router-dom';
import { styled } from 'styled-components';
import useUser from '../hooks/useUser';
import Resource from '../utils/Resource';
import Button from './Button';
import LoginModal from './LoginModal';

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
      <LogoContainer>
        <Link to="/">
          <Logo />
        </Link>
      </LogoContainer>
      <ButtonContainer>
        <RankButtonContainer>
          <Link to="/rank">
            <Button $fullWidth $fullHeight $variant="secondary">
              랭킹
            </Button>
          </Link>
        </RankButtonContainer>
        <LoginAndProfileButtonContainer>
          {user ? (
            <Link to="/my-profile">
              <Button $variant="outlined" $fullWidth $fullHeight>
                프로필
              </Button>
            </Link>
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
  align-items: center;
`;

const LogoContainer = styled.div`
  flex: 6;
`;

const Logo = styled.img.attrs({ src: Resource.getAssetUrl({ filename: 'logo.svg' }) })`
  height: ${({ theme }) => theme.fontSize['4xl']};
`;

const RankButtonContainer = styled.div`
  width: 44px;
  margin-right: ${({ theme }) => theme.space[2]};
`;

const LoginAndProfileButtonContainer = styled.div`
  width: 133px;
`;
