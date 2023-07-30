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

  const ref = useRef<HTMLImageElement>(null);
  const intersection = useIntersection(ref, { threshold: 0.7 });

  useEffect(() => {
    if (intersection) {
      onIntersect?.(intersection);
    }
  }, [intersection?.isIntersecting]);

  const handlePrevImage = () => {
    setCurrentImageIndex((prevIndex) => (prevIndex - 1 + cafe.images.length) % cafe.images.length);
  };

  const handleNextImage = () => {
    setCurrentImageIndex((prevIndex) => (prevIndex + 1 + cafe.images.length) % cafe.images.length);
  };

  return (
    <Container>
      <CarouselImage ref={ref} src={cafe.images[currentImageIndex]} />
      <DotsContainer>
        {cafe.images.map((_, index) => (
          <Dot key={index} active={index === currentImageIndex} onClick={() => setCurrentImageIndex(index)} />
        ))}
      </DotsContainer>
      <CarouselNavigation>
        <ButtonLeft>
          <BsChevronCompactLeft onClick={handlePrevImage} />
        </ButtonLeft>
        <ButtonRight>
          <BsChevronCompactRight onClick={handleNextImage} />
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

const CarouselImage = styled.img`
  overflow: hidden;
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

const CarouselNavigation = styled.div`
  position: absolute;
  z-index: 2;
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
  gap: ${({ theme }) => theme.space[5]};

  padding-bottom: ${({ theme }) => theme.space[10]};
`;

const ButtonLeft = styled.button``;

const ButtonRight = styled.button``;
