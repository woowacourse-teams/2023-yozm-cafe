import { PiCoffeeFill } from 'react-icons/pi';
import { styled } from 'styled-components';
import type { CafeMenuItem } from '../types';
import Image from '../utils/image';

type CafeMenuListProps = {
  menus: CafeMenuItem[];
};

const CafeMenuList = (props: CafeMenuListProps) => {
  const { menus } = props;

  const recommendedMenus = menus.filter((menuItem) => menuItem.isRecommended);
  const otherMenus = menus.filter((menuItem) => !menuItem.isRecommended);

  return (
    <MenuList>
      {recommendedMenus.map((menuItem) => (
        <MenuListItem key={menuItem.id} menuItem={menuItem} />
      ))}
      {otherMenus.map((menuItem) => (
        <MenuListItem key={menuItem.id} menuItem={menuItem} />
      ))}
    </MenuList>
  );
};

export default CafeMenuList;

const MenuList = styled.ul`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.space[3]};
`;

type MenuListItemProps = {
  menuItem: CafeMenuItem;
};

const MenuListItem = (props: MenuListItemProps) => {
  const { menuItem } = props;

  return (
    <MenuListItemContainer>
      {menuItem.imageUrl ? (
        <MenuItemImage src={Image.getUrl({ size: 100, filename: menuItem.imageUrl })} />
      ) : (
        <MenuItemImageAlt>
          <MenuItemImageAltIcon />
        </MenuItemImageAlt>
      )}
      <MenuItemName>{menuItem.name}</MenuItemName>
      <MenuItemDescription>{menuItem.description}</MenuItemDescription>
      <MenuItemPrice>{menuItem.price}</MenuItemPrice>
    </MenuListItemContainer>
  );
};

const MenuListItemContainer = styled.li`
  display: grid;
  grid-auto-rows: auto;
  grid-template-columns: 120px 1fr;
  grid-template-rows: auto 1fr;
  row-gap: ${({ theme }) => theme.space[1]};
  column-gap: ${({ theme }) => theme.space[2]};

  height: 120px;
  padding: ${({ theme }) => theme.space[2]};

  & > :first-child {
    grid-row: 1 / span 3;
    aspect-ratio: 1 / 1;
    height: 100%;
    border-radius: 8px;
  }
`;

const MenuItemImage = styled.img``;

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

const MenuItemName = styled.h3`
  margin-top: ${({ theme }) => theme.space[3]};
  font-size: ${({ theme }) => theme.fontSize.lg};
`;

const MenuItemDescription = styled.p`
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.color.gray};
`;

const MenuItemPrice = styled.p`
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.color.gray};
`;
