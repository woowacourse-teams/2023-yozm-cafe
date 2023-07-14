import { PiHouseLight, PiUserCircleLight } from 'react-icons/pi';
import { Link, useLocation } from 'react-router-dom';
import { styled } from 'styled-components';

const Navbar = () => {
  const { pathname } = useLocation();

  return (
    <Container>
      <IconContainer to="/" isactive={pathname === '/' ? 'true' : 'false'}>
        <Icon as={PiHouseLight} />
        <IconName>홈</IconName>
      </IconContainer>
      <IconContainer to="/login" isactive={pathname === '/login' ? 'true' : 'false'}>
        <Icon as={PiUserCircleLight} />
        <IconName>로그인</IconName>
      </IconContainer>
    </Container>
  );
};

export default Navbar;

const IconName = styled.span`
  font-size: small;
`;

const IconContainer = styled(Link)<{ isactive: string }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;

  color: ${({ isactive, theme }) => (isactive === 'true' ? theme.color.primary : theme.color.text.secondary)};
  text-decoration: none;
`;

const Container = styled.nav`
  display: flex;
  justify-content: space-evenly;

  width: 100%;
  height: 72px;
  padding: ${({ theme }) => theme.space['2']} ${({ theme }) => theme.space['24']};

  background-color: ${({ theme }) => theme.color.background.secondary};
`;

const Icon = styled.div`
  font-size: ${({ theme }) => theme.fontSize['4xl']};
`;
