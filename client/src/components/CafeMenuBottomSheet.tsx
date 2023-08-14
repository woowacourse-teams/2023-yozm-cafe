import { useEffect } from 'react';
import { createPortal } from 'react-dom';
import { BsX } from 'react-icons/bs';
import { styled } from 'styled-components';
import useCafeMenus from '../hooks/useCafeMenus';
import type { Theme } from '../styles/theme';
import type { Cafe } from '../types';
import CafeMenuList from './CafeMenuList';

type CafeMenuBottomSheetProps = {
  cafe: Cafe;
  onClose: () => void;
};

const CafeMenuBottomSheet = (props: CafeMenuBottomSheetProps) => {
  const { cafe, onClose } = props;
  const {
    data: { menus },
  } = useCafeMenus(cafe.id);

  useEffect(() => {
    document.addEventListener('click', onClose);

    return () => document.removeEventListener('click', onClose);
  }, [onClose]);

  const handlePreventClickPropagation: React.MouseEventHandler<HTMLDivElement> = (event) => {
    event.stopPropagation();
  };

  const recommendedMenus = menus.filter((menuItem) => menuItem.isRecommended);
  const otherMenus = menus.filter((menuItem) => !menuItem.isRecommended);

  return createPortal(
    <Container onClick={handlePreventClickPropagation}>
      <CloseButton>
        <CloseIcon onClick={onClose} />
      </CloseButton>
      <CafeMenuListTitle>대표 메뉴</CafeMenuListTitle>
      <CafeMenuList menus={recommendedMenus} />

      <Spacer $size={'8'} />

      <CafeMenuListTitle>메뉴</CafeMenuListTitle>
      <CafeMenuList menus={otherMenus} />
    </Container>,
    document.bodyRoot,
  );
};

export default CafeMenuBottomSheet;

const Container = styled.div`
  position: absolute;
  bottom: 0;

  overflow-y: auto;
  display: flex;
  flex-direction: column;

  width: 100%;
  height: 450px;
  padding: ${({ theme }) => theme.space[4]};
  padding-bottom: 0;

  color: ${({ theme }) => theme.color.text.primary};
  text-shadow: none;

  background: ${({ theme }) => theme.color.white};

  & svg {
    filter: none !important;
  }
`;

const CloseButton = styled.button`
  display: flex;
  justify-content: flex-end;
  width: 100%;
`;

const CloseIcon = styled(BsX)`
  cursor: pointer;
  font-size: ${({ theme }) => theme.fontSize['2xl']};
`;

const Spacer = styled.div<{ $size: keyof Theme['space'] }>`
  min-height: ${({ theme, $size }) => theme.space[$size]};
`;

const CafeMenuListTitle = styled.h2`
  margin-bottom: ${({ theme }) => theme.space[2]};
  font-size: ${({ theme }) => theme.fontSize.lg};
`;
