import { useEffect, useRef, useState } from 'react';
import { styled } from 'styled-components';
import useIntersection from '../hooks/useIntersection';
import type { Cafe } from '../types';
import Image from '../utils/Image';
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

  useEffect(() => {
    const handleScroll = () => {
      if (ref.current) {
        const { scrollLeft, clientWidth } = ref.current;
        const index = Math.round(scrollLeft / clientWidth);
        setCurrentImageIndex(index);
      }
    };

    ref.current?.addEventListener('scroll', handleScroll);
    return () => {
      ref.current?.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <Container>
      <CardQuantityContainer>
        <CardQuantityContents>
          {`${currentImageIndex + 1}`}/{cafe.images.length}
        </CardQuantityContents>
      </CardQuantityContainer>
      <CarouselImageList data-testid="cafe-card" ref={ref}>
        {cafe.images.map((image, index) => (
          <CarouselImage
            data-testid="carousel-image"
            key={index}
            src={Image.getUrl({ size: '500', filename: image })}
            alt={`${cafe}의 이미지`}
          />
        ))}
      </CarouselImageList>
      <DotsContainer data-testid="carousel-dots">
        {cafe.images.map((_, index) => (
          <Dot data-testid="dot" key={index} $active={index === currentImageIndex} />
        ))}
      </DotsContainer>
      <CafeSummary
        title={cafe.name}
        address={cafe.address}
        onClick={(event) => {
          event.stopPropagation();
          setIsShowDetail(true);
        }}
      />
      <CafeActionBar cafe={cafe} />
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

const CarouselImage = styled.img`
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
