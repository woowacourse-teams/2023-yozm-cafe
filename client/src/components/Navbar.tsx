import { Suspense, useState } from 'react';
import { FaSearch } from 'react-icons/fa';
import { FaRankingStar } from 'react-icons/fa6';
import { Link } from 'react-router-dom';
import { styled } from 'styled-components';
import useUser from '../hooks/useUser';
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
      <Link to="/">
        <Logo />
      </Link>
      <ButtonContainer>
        <Link to="/rank">
          <IconButton>
            <FaRankingStar />
          </IconButton>
        </Link>
        <Link to="/search">
          <IconButton>
            <FaSearch />
          </IconButton>
        </Link>
        <UserButtonContainer>
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
        </UserButtonContainer>
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
  gap: ${({ theme }) => theme.space[2]};
  align-items: center;
  margin-left: auto;
`;

const Logo = styled.img.attrs({ src: '/assets/logo.svg' })`
  height: ${({ theme }) => theme.fontSize['3xl']};
`;

const UserButtonContainer = styled.div`
  width: 100px;
`;

const IconButton = styled.button`
  cursor: pointer;

  display: flex;
  align-items: center;

  padding: ${({ theme }) => theme.space[2]};

  font-size: ${({ theme }) => theme.fontSize['2xl']};
  color: ${({ theme }) => theme.color.primary};
`;
