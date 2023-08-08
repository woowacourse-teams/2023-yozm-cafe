import { useEffect } from 'react';
import { BsBoxArrowUpRight, BsGeoAlt, BsX } from 'react-icons/bs';
import { styled } from 'styled-components';
import { Cafe } from '../types';
import OpeningHoursDetail from './OpeningHoursDetail';

type CafeDetailBottomSheetProps = {
  show?: boolean;
  cafe: Cafe;
  onClose: () => void;
};

const CafeDetailBottomSheet = (props: CafeDetailBottomSheetProps) => {
  const { show, cafe, onClose } = props;

  useEffect(() => {
    document.addEventListener('click', onClose);

    return () => document.removeEventListener('click', onClose);
  }, [onClose]);

  const handlePreventClickPropagation: React.MouseEventHandler<HTMLDivElement> = (event) => {
    event.stopPropagation();
  };

  return (
    <Container $show={show ?? false} onClick={handlePreventClickPropagation} role="dialog" aria-modal="true">
      <CloseButton>
        <CloseIcon onClick={onClose} />
      </CloseButton>
      <Title>{cafe.name}</Title>
      <InfoContainer>
        <LocationDetail>
          <BsGeoAlt />
          <a href={cafe.detail.mapUrl} target="_blank" rel="noopener noreferrer">
            <LocationName>
              {cafe.address} <BsBoxArrowUpRight />
            </LocationName>
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
      <Content>
        {cafe.detail.description.split('\n').map((paragraph, index) => (
          <p key={index}>{paragraph}</p>
        ))}
      </Content>

      <MoreContentHintGradient />
    </Container>
  );
};

export default CafeDetailBottomSheet;

const Container = styled.div<{ $show: boolean }>`
  position: absolute;
  bottom: 0;

  overflow-y: auto;
  display: ${(props) => (props.$show ? 'flex' : 'none')};
  flex-direction: column;

  width: 100%;
  height: 450px;
  padding: ${({ theme }) => theme.space[4]};
  padding-bottom: 0;

  color: ${({ theme }) => theme.color.text.primary};
  text-shadow: none;

  background: ${({ theme }) => theme.color.white};

  &::-webkit-scrollbar {
    display: none;
  }
  & svg {
    filter: none !important;
  }
`;

const Title = styled.h1`
  margin-bottom: ${({ theme }) => theme.space[4]};
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
  margin-top: ${({ theme }) => theme.space[5]};
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
