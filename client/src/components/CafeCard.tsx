import type { UIEventHandler } from 'react';
import { useCallback, useState } from 'react';
import { styled } from 'styled-components';
import type { Cafe } from '../types';
import Resource from '../utils/Resource';
import CafeActionBar from './CafeActionBar';
import CafeDetailBottomSheet from './CafeDetailBottomSheet';
import CafeSummary from './CafeSummary';

type CardProps = {
  cafe: Cafe;
};

const CafeCard = (props: CardProps) => {
  const { cafe } = props;

  const [isShowDetail, setIsShowDetail] = useState(false);
  const [currentImageIndex, setCurrentImageIndex] = useState(0);

  const handleScroll: UIEventHandler = useCallback((event) => {
    if (!(event.target instanceof HTMLDivElement)) return;

    const { scrollLeft, clientWidth } = event.target;
    const index = Math.round(scrollLeft / clientWidth);
    setCurrentImageIndex(index);
  }, []);

  return (
    <Container>
      <CardQuantityContainer>
        <CardQuantityContents>
          {`${currentImageIndex + 1}`}/{cafe.images.length}
        </CardQuantityContents>
      </CardQuantityContainer>

      <CarouselImageList onScroll={handleScroll}>
        {cafe.images.map((image, index) => (
          <CarouselImage
            key={index}
            src={Resource.getImageUrl({ size: '500', filename: image })}
            alt={`${cafe.name}의 ${index + 1}번째 이미지`}
            loading={Math.abs(currentImageIndex - index) <= 1 ? 'eager' : 'lazy'}
          />
        ))}
      </CarouselImageList>

      <Bottom>
        <BottomItem $fullWidth>
          <CafeSummary
            title={cafe.name}
            address={cafe.address}
            onClick={(event) => {
              event.stopPropagation();
              setIsShowDetail(true);
            }}
          />
        </BottomItem>
        <BottomItem $align="right">
          <CafeActionBar cafe={cafe} />
        </BottomItem>
      </Bottom>

      <DotsContainer>
        {cafe.images.map((_, index) => (
          <Dot key={index} $active={index === currentImageIndex} />
        ))}
      </DotsContainer>

      {isShowDetail && <CafeDetailBottomSheet cafe={cafe} onClose={() => setIsShowDetail(false)} />}
    </Container>
  );
};

export default CafeCard;

const Container = styled.div`
  position: relative;

  overflow: hidden;

  height: 100%;

  color: ${({ theme }) => theme.color.white};
  text-shadow: 0 0 2px rgba(0, 0, 0, 0.7);

  & svg {
    filter: drop-shadow(0 0 2px rgba(0, 0, 0, 0.7));
  }
`;

const CarouselImageList = styled.div`
  scroll-snap-type: x mandatory;

  overflow-x: auto;
  display: flex;

  width: 100%;
  height: 100%;
`;

const CarouselImage = styled.img.attrs({ draggable: false })`
  scroll-snap-align: start;
  scroll-snap-stop: always;

  flex: 0 0 100%;

  min-width: 100%;
  height: 100%;

  object-fit: cover;
`;

const DotsContainer = styled.div`
  position: absolute;
  bottom: ${({ theme }) => theme.space[5]};
  left: 50%;
  transform: translateX(-50%);

  display: flex;
  gap: ${({ theme }) => theme.space[2]};
`;

const Dot = styled.div<{ $active: boolean }>`
  cursor: pointer;

  width: 8px;
  height: 8px;

  background-color: ${({ $active, theme }) => ($active ? theme.color.white : theme.color.gray)};
  border-radius: 50%;
`;

const CardQuantityContainer = styled.div`
  position: absolute;

  display: flex;
  justify-content: flex-end;

  width: 100%;
  padding: ${({ theme }) => theme.space['2.5']} ${({ theme }) => theme.space['2.5']} 0 0;

  text-shadow: none;
`;

const CardQuantityContents = styled.div`
  padding: ${({ theme }) => theme.space[1]} ${({ theme }) => theme.space[2]};
  font-size: ${({ theme }) => theme.fontSize.xs};
  background-color: ${({ theme }) => theme.color.background.secondary};
  border-radius: 10px;
`;

const Bottom = styled.div`
  position: absolute;
  bottom: 40px;
  display: flex;
  width: 100%;
`;

const BottomItem = styled.div<{ $fullWidth?: boolean; $align?: 'left' | 'right' }>`
  position: absolute;
  bottom: 0;
  ${({ $align }) => $align === 'left' && 'left: 0;'}
  ${({ $align }) => $align === 'right' && 'right: 0;'}
  ${({ $fullWidth }) => $fullWidth && 'width: 100%;'}
`;
