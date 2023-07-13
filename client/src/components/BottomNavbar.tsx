import { BiCog, BiHeart, BiHomeAlt2, BiUser } from 'react-icons/bi';
import styled from 'styled-components';

const BottomNavbar = () => {
  return (
    <Container>
      <NavbarItemList>
        <NavbarItem>
          <BiHomeAlt2 size={32} />
        </NavbarItem>
        <NavbarItem>
          <BiHeart size={32} />
        </NavbarItem>
        <NavbarItem>
          <BiUser size={32} />
        </NavbarItem>
        <NavbarItem>
          <BiCog size={32} />
        </NavbarItem>
      </NavbarItemList>
    </Container>
  );
};

export default BottomNavbar;

const Container = styled.nav`
  position: relative;
  width: 100%;
  padding: ${({ theme }) => theme.space['2']};
  background: #d9d9d9;
`;

const NavbarItem = styled.li`
  padding: 8px;
  opacity: 0.5;
`;

const NavbarItemList = styled.ul`
  display: flex;
  justify-content: space-around;
`;
