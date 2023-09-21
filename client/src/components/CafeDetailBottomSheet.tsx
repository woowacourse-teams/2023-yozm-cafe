import { Suspense, useEffect } from 'react';
import { BsBoxArrowUpRight, BsGeoAlt, BsX } from 'react-icons/bs';
import { styled } from 'styled-components';
import useCafeMenus from '../hooks/useCafeMenus';
import type { Theme } from '../styles/theme';
import type { Cafe } from '../types';
import CafeMenuMiniList from './CafeMenuMiniList';
import OpeningHoursDetail from './OpeningHoursDetail';

type CafeDetailBottomSheetProps = {
  cafe: Cafe;
  onClose: () => void;
};

const CafeDetailBottomSheet = (props: CafeDetailBottomSheetProps) => {
  const { cafe, onClose } = props;

  useEffect(() => {
    document.addEventListener('click', onClose);

    return () => document.removeEventListener('click', onClose);
  }, [onClose]);

  const handlePreventClickPropagation: React.MouseEventHandler<HTMLDivElement> = (event) => {
    event.stopPropagation();
  };

  return (
    <Container onClick={handlePreventClickPropagation} role="dialog" aria-modal="true">
      <CloseButton data-testid="close-button">
        <CloseIcon onClick={onClose} />
      </CloseButton>
      <Title data-testid="cafe-name">{cafe.name}</Title>
      <Spacer $size={'4'} />
      <Suspense>
        <CafeMenu cafeId={cafe.id} />
      </Suspense>
      <InfoContainer>
        <LocationDetail>
          <BsGeoAlt />
          <a href={cafe.detail.mapUrl} target="_blank" rel="noopener noreferrer">
            <LocationName data-testid="cafe-address">
              {cafe.address} <BsBoxArrowUpRight />
            </LocationName>
          </a>
        </LocationDetail>
        <OpeningHoursDetails data-testid="cafe-openingHours">
          <OpeningHoursDetail openingHours={cafe.detail.openingHours} />
        </OpeningHoursDetails>
        {/* <Info>
          <BsTelephone />
          <h3>000-000-000</h3>
        </Info> */}
      </InfoContainer>
      <Spacer $size={'6'} />
      <Content data-testid="cafe-description">
        {cafe.detail.description.split('\n').map((paragraph, index) => (
          <p key={index}>{paragraph}</p>
        ))}
      </Content>

      <MoreContentHintGradient />
    </Container>
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
  cursor: pointer;
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
