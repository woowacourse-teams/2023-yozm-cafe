import { useEffect, useState } from 'react';
import { createPortal } from 'react-dom';
import { BsX } from 'react-icons/bs';
import { styled } from 'styled-components';
import useCafeMenus from '../hooks/useCafeMenus';
import type { Theme } from '../styles/theme';
import type { Cafe } from '../types';
import Resource from '../utils/Resource';
import CafeMenuList from './CafeMenuList';
import ImageModal from './ImageModal';

type CafeMenuBottomSheetProps = {
  cafe: Cafe;
  onClose: () => void;
};

const CafeMenuBottomSheet = (props: CafeMenuBottomSheetProps) => {
  const { cafe, onClose } = props;
  const {
    data: { menus, menuBoards },
  } = useCafeMenus(cafe.id);
  const [isImageModalOpen, setIsImageModalOpen] = useState(false);

  useEffect(() => {
    document.addEventListener('click', onClose);

    return () => document.removeEventListener('click', onClose);
  }, [onClose]);

  const handlePreventClickPropagation: React.MouseEventHandler<HTMLDivElement> = (event) => {
    event.stopPropagation();
  };

  const recommendedMenus = menus.filter((menuItem) => menuItem.isRecommended);
  const otherMenus = menus.filter((menuItem) => !menuItem.isRecommended);

  return (
    <>
      {createPortal(
        <Container onClick={handlePreventClickPropagation}>
          <CloseButton>
            <CloseIcon onClick={onClose} />
          </CloseButton>

          {menuBoards.length > 0 && (
            <>
              <ShowMenuBoardButton $imageUrl={menuBoards[0].imageUrl} onClick={() => setIsImageModalOpen(true)}>
                메뉴판 이미지로 보기 ({menuBoards.length})
              </ShowMenuBoardButton>
              <Spacer $size={'8'} />
            </>
          )}

          {recommendedMenus.length > 0 && (
            <>
              <CafeMenuListTitle>대표 메뉴</CafeMenuListTitle>
              <CafeMenuList menus={recommendedMenus} />
              <Spacer $size={'8'} />
            </>
          )}

          {otherMenus.length > 0 && (
            <>
              <CafeMenuListTitle>메뉴</CafeMenuListTitle>
              <CafeMenuList menus={otherMenus} />
              <Spacer $size={'8'} />
            </>
          )}

          {menus.length === 0 && (
            <>
              <Spacer $size={'4'} />
              <Placeholder>등록된 메뉴가 없습니다</Placeholder>
            </>
          )}
        </Container>,
        document.bodyRoot,
      )}

      {isImageModalOpen &&
        createPortal(
          <ImageModal
            imageUrls={menuBoards.map((menuBoard) => menuBoard.imageUrl)}
            onClose={() => setIsImageModalOpen(false)}
          />,
          document.bodyRoot,
        )}
    </>
  );
};

export default CafeMenuBottomSheet;

const Container = styled.div`
  position: absolute;
  bottom: 0;

  overflow-y: auto;
  display: flex;
  flex-direction: column;
  align-items: flex-start;

  width: 100%;
  height: 600px;
  max-height: 100vh;
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
  font-size: ${({ theme }) => theme.fontSize['2xl']};
`;

const Spacer = styled.div<{ $size: keyof Theme['space'] }>`
  min-height: ${({ theme, $size }) => theme.space[$size]};
`;

const CafeMenuListTitle = styled.h2`
  margin-bottom: ${({ theme }) => theme.space[2]};
  font-size: ${({ theme }) => theme.fontSize.lg};
`;

const Placeholder = styled.div`
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.color.gray};
`;

const ShowMenuBoardButton = styled.button<{ $imageUrl: string }>`
  padding: ${({ theme }) => theme.space[5]} ${({ theme }) => theme.space[10]};

  font-size: ${({ theme }) => theme.fontSize.lg};
  color: ${({ theme }) => theme.color.white};

  background: linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.6)),
    url(${({ $imageUrl }) => Resource.getImageUrl({ size: 'original', filename: $imageUrl })});
  background-repeat: no-repeat;
  background-position: center;
  background-size: 100%;
  border-radius: 8px;

  transition: background-size 0.3s;

  &:hover {
    background-size: 110%;
  }
`;
