import { Suspense, useEffect } from 'react';
import { createPortal } from 'react-dom';
import { BsX } from 'react-icons/bs';
import { styled } from 'styled-components';
import { useScrollSnapGuard } from 'yozm-cafe-react-scroll-snap';
import useCafeMenus from '../hooks/useCafeMenus';
import type { Theme } from '../styles/theme';
import type { Cafe } from '../types';
import CafeMenuMiniList from './CafeMenuMiniList';
import OpeningHoursDetail from './OpeningHoursDetail';
import QueryErrorBoundary from './QueryErrorBoundary';

type CafeDetailBottomSheetProps = {
  cafe: Cafe;
  onClose: () => void;
};

const CafeDetailBottomSheet = (props: CafeDetailBottomSheetProps) => {
  const { cafe, onClose } = props;
  const scrollSnapGuardHandlers = useScrollSnapGuard();

  useEffect(() => {
    document.addEventListener('click', onClose);

    return () => document.removeEventListener('click', onClose);
  }, [onClose]);

  const handlePreventClickPropagation: React.MouseEventHandler<HTMLDivElement> = (event) => {
    event.stopPropagation();
  };

  return (
    <>
      {createPortal(
        <Container onClick={handlePreventClickPropagation} role="dialog" aria-modal="true" {...scrollSnapGuardHandlers}>
          <CloseButton>
            <CloseIcon onClick={onClose} />
          </CloseButton>
          <Title>{cafe.name}</Title>
          <Spacer $size={'4'} />
          <QueryErrorBoundary>
            <Suspense>
              <CafeMenu cafeId={cafe.id} />
            </Suspense>
          </QueryErrorBoundary>
          <InfoContainer>
            <LocationDetail>
              <NaverMapIcon />
              <a href={cafe.detail.mapUrl} target="_blank" rel="noopener noreferrer">
                <LocationName>{cafe.address}</LocationName>
              </a>
            </LocationDetail>
            <OpeningHoursDetails>
              <OpeningHoursDetail openingHours={cafe.detail.openingHours} />
            </OpeningHoursDetails>
            {/* <Info>
          <BsTelephone />
          <h3>000-000-000</h3>
        </Info> */}
          </InfoContainer>
          <Spacer $size={'6'} />
          <Content>
            {cafe.detail.description.split('\n').map((paragraph, index) => (
              <p key={index}>{paragraph}</p>
            ))}
          </Content>

          <MoreContentHintGradient />
        </Container>,
        document.bodyRoot,
      )}
    </>
  );
};

export default CafeDetailBottomSheet;

const Container = styled.div`
  position: absolute;
  bottom: 0;

  overflow-y: auto;
  display: flex;
  flex-direction: column;

  width: 100%;
  height: 450px;
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

const Spacer = styled.div<{ $size: keyof Theme['space'] }>`
  min-height: ${({ theme, $size }) => theme.space[$size]};
`;

const Title = styled.h1`
  font-size: ${({ theme }) => theme.fontSize['3xl']};
  font-weight: bolder;
`;

const InfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.space[2]};
`;

const OpeningHoursDetails = styled.div`
  display: flex;
  gap: ${({ theme }) => theme.space[2]};
  align-items: flex-start;
`;

const LocationDetail = styled.div`
  display: flex;
  gap: ${({ theme }) => theme.space[2]};
  align-items: center;
  color: ${({ theme }) => theme.color.secondary};
`;

const NaverMapIcon = styled.img.attrs({ src: '/assets/naver-map-icon.png', alt: '네이버 지도 아이콘' })`
  width: 16px;
  height: 16px;
`;

const LocationName = styled.h3`
  display: flex;
  gap: ${({ theme }) => theme.space[1]};
  align-items: center;
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.space[2]};
`;

const MoreContentHintGradient = styled.div`
  position: sticky;
  bottom: 0;

  width: 100%;
  min-height: ${({ theme }) => theme.space[16]};

  background: linear-gradient(transparent, white);
`;

const CloseButton = styled.button`
  display: flex;
  justify-content: flex-end;
  width: 100%;
`;

const CloseIcon = styled(BsX)`
  font-size: ${({ theme }) => theme.fontSize['2xl']};
`;

type CafeMenuProps = {
  cafeId: Cafe['id'];
};

const CafeMenu = (props: CafeMenuProps) => {
  const { cafeId } = props;
  const {
    data: { menus },
  } = useCafeMenus(cafeId);

  const recommendedMenus = menus.filter((menuItem) => menuItem.isRecommended);

  return (
    recommendedMenus.length > 0 && (
      <>
        <CafeMenuMiniList menus={recommendedMenus} />
        <Spacer $size={'8'} />
      </>
    )
  );
};
