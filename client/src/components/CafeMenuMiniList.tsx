import { PiCoffeeFill } from 'react-icons/pi';
import { styled } from 'styled-components';
import type { CafeMenuItem } from '../types';
import Image from '../utils/Image';

type CafeMenuMiniListProps = {
  menus: CafeMenuItem[];
};

const CafeMenuMiniList = (props: CafeMenuMiniListProps) => {
  const { menus } = props;

  return (
    <MenuList>
      {menus.map((menuItem) => (
        <MenuListItem key={menuItem.id} menuItem={menuItem} />
      ))}
    </MenuList>
  );
};

export default CafeMenuMiniList;

const MenuList = styled.ul`
  overflow-y: auto;
  display: flex;
  flex-shrink: 0;
  gap: ${({ theme }) => theme.space[4]};
`;

type MenuListItemProps = {
  menuItem: CafeMenuItem;
};

const MenuListItem = (props: MenuListItemProps) => {
  const { menuItem } = props;

  return (
    <MenuListItemContainer>
      <MenuItemImageContainer>
        {menuItem.imageUrl ? (
          <MenuItemImage src={Image.getUrl({ size: '100', filename: menuItem.imageUrl })} />
        ) : (
          <MenuItemImageAlt>
            <MenuItemImageAltIcon />
          </MenuItemImageAlt>
        )}
      </MenuItemImageContainer>
      <MenuItemName>{menuItem.name}</MenuItemName>
    </MenuListItemContainer>
  );
};

const MenuListItemContainer = styled.li`
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  align-items: center;

  width: 70px;
  height: 100%;
`;

const MenuItemImageContainer = styled.div`
  width: 100%;
  height: 100%;
  padding: ${({ theme }) => theme.space[2]};

  & > * {
    aspect-ratio: 1 / 1;
    width: 100%;
  }
`;

const MenuItemImage = styled.img`
  display: block;
  object-fit: cover;
  border-radius: 50%;
`;

const MenuItemImageAlt = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  background: #dedede;
  border-radius: 50%;
`;

const MenuItemImageAltIcon = styled(PiCoffeeFill)`
  width: 50%;
  height: 50%;
`;

const MenuItemName = styled.div`
  font-size: ${({ theme }) => theme.fontSize.xs};
`;

const Divider = styled.hr`
  margin: ${({ theme }) => theme.space[2]} ${({ theme }) => theme.space[1]};
  border: none;
  border-left: 1px solid ${({ theme }) => theme.color.gray};
`;
