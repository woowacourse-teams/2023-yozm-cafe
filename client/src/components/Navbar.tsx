import { Suspense, useState } from 'react';
import { Link } from 'react-router-dom';
import { styled } from 'styled-components';
import useUser from '../hooks/useUser';
import Button from './Button';
import Logo from './Logo';
import Modal from './Modal';

const Navbar = () => {
  const { data: user } = useUser();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
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
          <Button fullWidth={true} fullHeight={true} onClick={openModal}>
            로그인
          </Button>
        )}
      </ButtonContainer>
      <Suspense>{isModalOpen && <Modal onClose={closeModal} />}</Suspense>
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
  padding: 0 15px;
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
