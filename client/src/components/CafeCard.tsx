import { useEffect, useRef, useState } from 'react';
import { BsChevronCompactLeft, BsChevronCompactRight } from 'react-icons/bs';
import { styled } from 'styled-components';
import useIntersection from '../hooks/useIntersection';
import { Cafe } from '../types';
import CafeActionBar from './CafeActionBar';
import CafeDetailBottomSheet from './CafeDetailBottomSheet';
import CafeSummary from './CafeSummary';

type CardProps = {
  cafe: Cafe;
  onIntersect?: (intersection: IntersectionObserverEntry) => void;
};

const CafeCard = (props: CardProps) => {
  const { cafe, onIntersect } = props;
  const [isShowDetail, setIsShowDetail] = useState(false);
  const [currentImageIndex, setCurrentImageIndex] = useState(0);

  const ref = useRef<HTMLDivElement>(null);
  const intersection = useIntersection(ref, { threshold: 0.7 });

  useEffect(() => {
    if (intersection) {
      onIntersect?.(intersection);
    }
  }, [intersection?.isIntersecting]);

  const getImageIndexByOffset = (offset: number) => {
    const length = cafe.images.length;
    return (currentImageIndex + length + offset) % length;
  };

  const getImageByOffset = (offset: number) => cafe.images[getImageIndexByOffset(offset)];

  const handlePrevImage = () => {
    setCurrentImageIndex(getImageIndexByOffset(-1));
  };

  const handleNextImage = () => {
    setCurrentImageIndex(getImageIndexByOffset(1));
  };

  return (
    <Container>
      <CarouselImageList ref={ref}>
        <CarouselImage src={getImageByOffset(0)} alt={`Cafe Image ${currentImageIndex + 1}`} $show={true} />
        {/* 이미지를 미리 불러오기 위한 장치 */}
        <CarouselImage src={getImageByOffset(-1)} $show={false} aria-hidden="true" />
        <CarouselImage src={getImageByOffset(1)} $show={false} aria-hidden="true" />
      </CarouselImageList>
      <DotsContainer>
        {cafe.images.map((_, index) => (
          <Dot
            key={index}
            active={index === currentImageIndex}
            onClick={() => setCurrentImageIndex(index)}
            role="button"
            tabIndex={0}
            aria-label={`Cafe Image ${index + 1}`}
          />
        ))}
      </DotsContainer>
      <CarouselNavigation>
        <ButtonLeft onClick={handlePrevImage} aria-label="이전 이미지">
          <BsChevronCompactLeft />
        </ButtonLeft>
        <ButtonRight onClick={handleNextImage} aria-label="다음 이미지">
          <BsChevronCompactRight />
        </ButtonRight>
      </CarouselNavigation>
      <AsidePosition>
        <Aside>
          <CafeSummary
            title={cafe.name}
            address={cafe.address}
            onClick={(event) => {
              event.stopPropagation();
              setIsShowDetail(true);
            }}
          />
          <CafeActionBar cafe={cafe} />
        </Aside>
        <CafeDetailBottomSheet show={isShowDetail} cafe={cafe} onClose={() => setIsShowDetail(false)} />
      </AsidePosition>
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
  display: grid;
  width: 100%;
  height: 100%;
`;

const CarouselImage = styled.img<{ $show: boolean }>`
  display: ${(props) => (props.$show ? 'initial' : 'none')};
  grid-area: 1 / 1 / 1 / 1;

  width: 100%;
  height: 100%;

  object-fit: cover;
`;

const CarouselNavigation = styled.div`
  position: absolute;
  top: 50%;

  display: flex;
  justify-content: space-between;

  width: 100%;
  & > * {
    cursor: pointer;

    font-size: ${({ theme }) => theme.fontSize['3xl']};
    color: ${({ theme }) => theme.color.white};

    background: none;
    border: none;
  }
`;

const DotsContainer = styled.div`
  position: absolute;
  bottom: ${({ theme }) => theme.space[5]};
  left: 50%;
  transform: translateX(-50%);

  display: flex;
  gap: ${({ theme }) => theme.space[2]};
`;

const Dot = styled.div<{ active: boolean }>`
  cursor: pointer;

  width: 8px;
  height: 8px;

  background-color: ${({ active, theme }) => (active ? theme.color.white : theme.color.gray)};
  border-radius: 50%;
`;

const AsidePosition = styled.div`
  position: absolute;
  bottom: 0;
  width: 100%;
`;

const Aside = styled.div`
  position: relative;
  display: flex;
  flex-direction: column-reverse;
  padding-bottom: ${({ theme }) => theme.space[10]};
`;

const ButtonLeft = styled.button``;

const ButtonRight = styled.button``;
