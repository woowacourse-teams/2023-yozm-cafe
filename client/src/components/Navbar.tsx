import { PiHouseLight, PiUserCircleLight } from 'react-icons/pi';
import { Link, useLocation } from 'react-router-dom';
import { styled } from 'styled-components';

const Navbar = () => {
  const { pathname } = useLocation();

  return (
    <Container>
      <IconContainer to="/" $isActive={pathname === '/'}>
        <Icon as={PiHouseLight} />
        <IconName>홈</IconName>
      </IconContainer>
      <IconContainer to="/login" $isActive={pathname === '/login'}>
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
  height: 72px;

  background-color: ${({ theme }) => theme.color.white};
`;

const Icon = styled.div`
  font-size: ${({ theme }) => theme.fontSize['4xl']};
`;
