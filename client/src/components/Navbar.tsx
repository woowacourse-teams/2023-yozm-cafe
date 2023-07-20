import { useQuery } from '@tanstack/react-query';
import { PiHouseLight, PiUserCircleLight } from 'react-icons/pi';
import { Link, useLocation } from 'react-router-dom';
import { styled } from 'styled-components';
import client from '../client';

const Navbar = () => {
  const { pathname } = useLocation();

  const { data: isLoggedIn } = useQuery<boolean>(['isLoggedIn'], {
    enabled: false,
    initialData: client.accessToken !== null,
  });

  return (
    <Container>
      <IconContainer to="/" $isActive={pathname === '/'}>
        <Icon as={PiHouseLight} />
        <IconName>홈</IconName>
      </IconContainer>
      {isLoggedIn ? (
        <IconContainer to="/my-profile" $isActive={pathname === '/profile'}>
          <ProfileImage src="/images/profile-example.png" alt="Profile" />
          <IconName>프로필</IconName>
        </IconContainer>
      ) : (
        <IconContainer to="/login" $isActive={pathname === '/login'}>
          <Icon as={PiUserCircleLight} />
          <IconName>로그인</IconName>
        </IconContainer>
      )}
    </Container>
  );
};

export default Navbar;

const IconName = styled.span`
  font-size: small;
`;

const IconContainer = styled(Link)<{ $isActive: boolean }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;

  color: ${({ $isActive, theme }) => ($isActive ? theme.color.primary : theme.color.gray)};
  text-decoration: none;
`;

const Container = styled.nav`
  display: flex;
  justify-content: space-evenly;

  width: 100%;
  height: 66px;

  border-top: 1px solid ${({ theme }) => theme.color.line.secondary};
`;

const Icon = styled.div`
  font-size: ${({ theme }) => theme.fontSize['4xl']};
`;

const ProfileImage = styled.img`
  width: 36px;
  height: 36px;
  border-radius: 50%;
`;
