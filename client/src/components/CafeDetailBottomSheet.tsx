import { useEffect } from 'react';
import { BsBoxArrowUpRight, BsClock, BsGeoAlt } from 'react-icons/bs';
import { styled } from 'styled-components';
import { Cafe } from '../types';
import OpeningHoursDetail from './OpeningHoursDetail';

type CafeDetailBottomSheetProps = {
  show?: boolean;
  cafe: Cafe;
  onClose: () => void;
};

const CafeDetailBottomSheet = ({ show, cafe, onClose }: CafeDetailBottomSheetProps) => {
  useEffect(() => {
    document.addEventListener('click', onClose);

    return () => document.removeEventListener('click', onClose);
  }, [onClose]);

  const handlePreventClickPropagation: React.MouseEventHandler<HTMLDivElement> = (event) => {
    event.stopPropagation();
  };

  return (
    <Container $show={show ?? false} onClick={handlePreventClickPropagation}>
      <Title>{cafe.name}</Title>
      <InfoContainer>
        <Info>
          <BsGeoAlt />
          <a href={cafe.detail.mapUrl} target="_blank">
            <h3>
              {cafe.address} <BsBoxArrowUpRight />
            </h3>
          </a>
        </Info>
        <Info>
          <BsClock />
          <OpeningHoursDetail openingHours={cafe.detail.openingHours} />
        </Info>
        {/* <Info>
          <BsTelephone />
          <h3>000-000-000</h3>
        </Info> */}
      </InfoContainer>
      <Content>
        {cafe.detail.description.split('\n').map((paragraph) => (
          <p>{paragraph}</p>
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
  padding-bottom: ${({ theme }) => theme.space[16]};

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

const Info = styled.div`
  display: flex;
  gap: ${({ theme }) => theme.space[2]};
  align-items: flex-start;
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.space[2]};
  margin-top: ${({ theme }) => theme.space[5]};
`;

const MoreContentHintGradient = styled.div`
  position: fixed;
  bottom: 0;

  width: 475px; /* FIXME: 하드코딩 대신 부모 크기 기반으로 설정하도록 변경하기 */
  height: ${({ theme }) => theme.space[16]};

  background: linear-gradient(transparent, white);
`;
